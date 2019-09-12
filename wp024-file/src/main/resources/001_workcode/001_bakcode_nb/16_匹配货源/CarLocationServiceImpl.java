package com.zrytech.framework.app.service.impl;

import com.zrytech.framework.app.constants.CargoConstant;
import com.zrytech.framework.app.entity.*;
import com.zrytech.framework.app.mapper.CargoMapper;
import com.zrytech.framework.app.mapper.LoadingMapper;
import com.zrytech.framework.app.mapper.OftenAddressMapper;
import com.zrytech.framework.app.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.zrytech.framework.app.dto.carlocation.CarLocationAddDto;
import com.zrytech.framework.app.dto.carlocation.CarLocationPageDto;
import com.zrytech.framework.app.service.CarLocationService;
import com.zrytech.framework.app.service.CarService;
import com.zrytech.framework.base.entity.PageData;
import com.zrytech.framework.base.entity.ServerResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CarLocationServiceImpl implements CarLocationService {

	@Autowired private CarLocationRepository carLocationRepository;
	
	@Autowired private CarService carService;

	@Autowired
    private WaybillDetailRepository waybillDetailRepository;

	@Autowired
    private CarRepository carRepository;

	@Autowired
	private WaybillRepository waybillRepository;

	@Autowired
	private CargoRepository cargoRepository;

//	@Autowired
//	private LoadingMapper loadingMapper;

	@Autowired
	private CarCargoOwnnerRepository carCargoOwnnerRepository;

	@Autowired
	private OftenAddressMapper oftenAddressMapper;

	@Autowired
	private CargoMapper cargoMapper;

	@Autowired
	private CargoSendRecordRepository cargoSendRecordRepository;

	/**
	 * 车辆位置分页
	 * @author cat
	 * 
	 * @param dto	车牌号
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public ServerResponse page(CarLocationPageDto dto, Integer pageNum, Integer pageSize){
		CarLocation carLocation = new CarLocation();
		BeanUtils.copyProperties(dto, carLocation);
		
		Sort sort = new Sort(Direction.DESC, "createDate");
		Pageable pageable = new PageRequest(pageNum - 1, pageSize, sort);
		
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("carNo", GenericPropertyMatchers.contains());
		
		Example<CarLocation> example = Example.of(carLocation, matcher);
		
		Page<CarLocation> page = carLocationRepository.findAll(example, pageable);
		
		PageData<CarLocation> pageData = new PageData<CarLocation>();
		pageData.setList(page.getContent());
		pageData.setPageNo(page.getNumber() + 1);
		pageData.setPageSize(page.getSize());
		pageData.setTotal(page.getTotalElements());
		
		return ServerResponse.successWithData(pageData);
	}
	
	
	/**
	 * 车辆位置分页
	 * @author cat
	 * 
	 * @param carId	车辆Id
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public ServerResponse page(Integer carId, Integer pageNum, Integer pageSize) {
		Car car = carService.assertCarAvailable(carId);
		String carNo = car.getCarNo();
		CarLocationPageDto dto = new CarLocationPageDto();
		dto.setCarNo(carNo);
		return this.page(dto , pageNum, pageSize);
	}
	
	
	/**
	 * 新增车辆位置
	 * @author cat
	 * 
	 * @param dto	车辆位置信息
	 * @return
	 */
	@Override
	public ServerResponse add(CarLocationAddDto dto) {
		CarLocation carLocation = new CarLocation();
		BeanUtils.copyProperties(dto, carLocation);
		carLocation.setCreateDate(new Date());
		carLocationRepository.save(carLocation);

        List<Car> carList = carRepository.findByCarNo(carLocation.getCarNo());

        //TODO 到达目的地附近后匹配货源
		matchCargo(carLocation, carList);

        return ServerResponse.successWithData("添加成功");
	}

	private void matchCargo(CarLocation carLocation, List<Car> carList) {
		Car car = null;
		if(carList!=null && carList.size()>0){
			car = carList.get(0);
		}
		//获得车辆所属运单项
		WaybillDetail waybillDetail = waybillDetailRepository.findByCarId(car.getId());
		if(waybillDetail!=null){
			Integer waybillId = waybillDetail.getWaybillId();
			Waybill waybill = waybillRepository.findOne(waybillId);

			if (waybill!=null) {
				//取得货源
				Integer cargoId = waybill.getCargoId();
				Cargo cargo = cargoRepository.findOne(cargoId);

				//todo
//				List<Loading> loadingList = loadingMapper.selectLoadingList(cargoId, CargoConstant.UNLOADING_TYPE);
				//取得货源目的地
				String endProvince = cargo.getEndProvince();
				String endCity = cargo.getEndCity();

				//取得最后一个卸货地
//				if(loadingList!=null && loadingList.size()>0){
//					Loading loading = loadingList.get(0);

					//匹配车主
					if (endProvince.equalsIgnoreCase(carLocation.getProvince()) && endCity.equalsIgnoreCase(carLocation.getCity())){
						log.info("车辆已接近目的地,开始匹配货源:");
						Integer carOwnerId = car.getCarOwnerId();
						CarCargoOwnner carOwnner = carCargoOwnnerRepository.findOne(carOwnerId);
						Integer customerId = carOwnner.getCustomerId();

						//取得车主常用地址列表
						List<OftenAddress> oftenAddressList = oftenAddressMapper.selectByCustomerId(customerId);

						//start
						matchPlace(carLocation, carOwnerId, oftenAddressList);
						//end
					}

				}

			}

//		}
	}

	//匹配省市
	private void matchPlace(CarLocation carLocation, Integer carOwnerId, List<OftenAddress> oftenAddressList) {
		if(oftenAddressList!=null && oftenAddressList.size()>0){
            //取得出发城市为当前车辆所在城市的货源
            List<Integer> cargoIdList = cargoMapper.carSearchByPlace(carLocation.getProvince(), carLocation.getCity());

            for (OftenAddress oftenAddress:
                 oftenAddressList) {

                if (oftenAddress.getBeginCity().equalsIgnoreCase(carLocation.getCity())){
                    log.info("当前城市与车主常用路线出发省市匹配");
                    List<CargoSendRecord> cargoSendRecordList = new ArrayList<>();
                    if(cargoIdList!=null && cargoIdList.size()>0){
                        for (Integer cargoIdMatch:
                                cargoIdList) {
                            cargoSendRecordList.add(new CargoSendRecord(cargoIdMatch,carOwnerId,new Date()));
                        }
                    }
                    cargoSendRecordRepository.save(cargoSendRecordList);

                    break;
                }

            }
        }
	}


}
