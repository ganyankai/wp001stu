package com.catt.oil.service.base.lockPrice;

import com.catt.common.base.pojo.search.Page;
import com.catt.common.base.pojo.search.Pageable;
import com.catt.common.base.service.BaseService;
import com.catt.oil.repository.entity.lockPriceMgr.LockPrice;
import com.catt.oil.repository.entity.oilStationMgr.OilstationInfo;
import com.catt.oil.repository.form.lockPrice.LockPriceForm;

import java.util.List;
import java.util.Map;

/**
 * 锁价管理接口
 */
public interface LockPriceService extends BaseService<LockPrice, String> {

    //根据锁价id查询所有的油站
    public List findOilStationList(String id);

    //根据集团id查询所有的油站
    public List findOilStationListByGroup(String groupId);

    Page<Map> lockPriceList(LockPriceForm lockPriceForm, Pageable pageable);

    void updateLockPrice(LockPriceForm lockPriceForm);

    void addLockPrice(LockPriceForm lockPriceForm);

    @Override
    LockPrice find(String id);

    void delete(String id);

    void updateStatus(String id,String status);



}
