package com.catt.oil.service.impl.base.lockPrice;

import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.catt.common.base.pojo.search.Page;
import com.catt.common.base.pojo.search.Pageable;
import com.catt.common.base.service.impl.BaseServiceImpl;
import com.catt.common.module.enumMgr.pojo.EnumTransformData;
import com.catt.common.module.enumMgr.service.EnumMgrService;
import com.catt.oil.repository.dao.lockPrice.LockPriceDao;
import com.catt.oil.repository.entity.lockPriceMgr.LockPrice;
import com.catt.oil.repository.form.lockPrice.LockPriceForm;
import com.catt.oil.service.base.lockPrice.LockPriceService;

@Service("lockPriceServiceImpl")
public class LockPriceServiceImpl extends BaseServiceImpl<LockPrice, String>  implements LockPriceService {
    @Resource(name = "lockPriceDaoImpl")
    private LockPriceDao lockPriceDao;

    @Resource
    public void setBaseDao(LockPriceDao lockPriceDao) {
        super.setBaseDao(lockPriceDao);
    }


    /**
     * 枚举服务层对象
     */
    @Resource(name = "enumMgrServiceImpl")
    private EnumMgrService enumMgrService;


    @Override
    public List findOilStationList(String id) {
        return lockPriceDao.findOilStationList(id);
    }

    @Override
    public List findOilStationListByGroup(String groupId) {
        return lockPriceDao.findOilStationListByGroup(groupId);
    }

    @Override
    public Page<Map> lockPriceList(LockPriceForm lockPriceForm, Pageable pageable) {
        Page<Map> mapPage = lockPriceDao.lockPriceList(lockPriceForm, pageable);
        List<Map> mapList = mapPage.getContent();
        enumMgrService.transformEnum(mapList,
                new ArrayList<EnumTransformData>() {
                    {
                        add(new EnumTransformData("T_OILSTATION_PRODUCT",
                                "I_KIND", "productKind",
                                "productKindName"));
                    }
                });

        enumMgrService.transformEnum(mapList,
                new ArrayList<EnumTransformData>() {
                    {
                        add(new EnumTransformData("T_LOCK_PRICE",
                                "STATUS", "status",
                                "statusName"));
                    }
                });
        return mapPage;
    }

    @Override
    public void updateLockPrice(LockPriceForm lockPriceForm) {
        LockPrice lockPrice = new LockPrice();
        BeanUtils.copyProperties(lockPriceForm,lockPrice,LockPrice.class);
        lockPrice.setCreateDate(new Date());

        String oids = lockPriceForm.getOids();

        //先删除再添加
        String delSql = "delete from t_lock_oilstation where LOCK_PRICE_ID = '"+lockPriceForm.getId()+"'";
        System.out.println("delSql:"+delSql);
        lockPriceDao.executeUpdateBySql(delSql.toString(),new HashMap<>());

        if(StringUtils.isNotBlank(oids)){
            String[] strs = oids.split(",");
            for (String str:
                    strs) {
                String uuid = UUID.randomUUID().toString();
                String sql = "insert into `t_lock_oilstation`(`S_ID`,`LOCK_PRICE_ID`, `STATION_ID`, `D_CREATE_DATE`, `D_MODIFY_DATE`) VALUES (" +
                        "'" +uuid+"'," +"'" +lockPriceForm.getId()+"'," +"'" +str+"',"+ " now(),now()"+
                        ")";
                System.out.println("sql:"+sql);
                lockPriceDao.executeUpdateBySql(sql.toString(),new HashMap<>());
            }
        }
    }

    @Override
    public void addLockPrice(LockPriceForm lockPriceForm) {
        LockPrice lockPrice = new LockPrice();
        BeanUtils.copyProperties(lockPriceForm,lockPrice,LockPrice.class);
        lockPrice.setCreateDate(new Date());

        LockPrice price = lockPriceDao.merge(lockPrice);

        //新增锁价从表
        String priceId = lockPriceDao.getIdentifier(price);
        String oids = lockPriceForm.getOids();

        if(StringUtils.isNotBlank(oids)){
            String[] strs = oids.split(",");
            for (String str:
                 strs) {
                String uuid = UUID.randomUUID().toString();
                String sql = "insert into `t_lock_oilstation`(`S_ID`,`LOCK_PRICE_ID`, `STATION_ID`, `D_CREATE_DATE`, `D_MODIFY_DATE`) VALUES (" +
                        "'" +uuid+"'," +"'" +priceId+"'," +"'" +str+"',"+ " now(),now()"+
                        ")";
                System.out.println("sql:"+sql);
                lockPriceDao.executeUpdateBySql(sql.toString(),new HashMap<>());
            }
        }

    }

    @Override
    public LockPrice find(String id){
        return lockPriceDao.find(id);
    }

    @Override
    public void updateStatus(String id,String status) {
        LockPrice lockPrice = find(id);
        lockPrice.setStatus(Integer.parseInt(status));
        lockPriceDao.saveOrUpdate(lockPrice);
    }



}
