package com.catt.oil.repository.dao.lockPrice;

import com.catt.common.base.pojo.search.Page;
import com.catt.common.base.pojo.search.Pageable;
import com.catt.common.base.repository.dao.BaseDao;
import com.catt.oil.repository.entity.lockPriceMgr.LockPrice;
import com.catt.oil.repository.form.lockPrice.LockPriceForm;

import java.util.List;
import java.util.Map;

public interface LockPriceDao extends BaseDao<LockPrice, String> {

    Page<Map> lockPriceList(LockPriceForm lockPriceForm, Pageable pageable);

    List findOilStationList(String id);

    List findOilStationListByGroup(String groupId);


}
