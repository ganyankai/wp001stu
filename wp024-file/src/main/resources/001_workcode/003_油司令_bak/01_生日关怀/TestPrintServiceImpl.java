package com.catt.oil.settlement.service.impl.stationPrice;

import com.catt.common.base.pojo.search.Filter;
import com.catt.common.util.collections.CollectionUtil;
import com.catt.common.util.collections.MapUtil;
import com.catt.common.util.json.JsonUtils;
import com.catt.oil.repository.data.CusInfoVo;
import com.catt.oil.repository.entity.oilStationMgr.OilstationPrice;
import com.catt.oil.repository.entity.oilStationMgr.OilstationProduct;
import com.catt.oil.service.base.oilStationMgr.OilstationPriceBaseService;
import com.catt.oil.service.base.oilStationMgr.OilstationProductBaseService;
import com.catt.oil.service.customerMgr.CusInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 说明：油价定时
 *
 * @author zhoumingxiang
 * @version 2019/04/02
 */
@Service("testPrintServiceImpl")
public class TestPrintServiceImpl {
    /**
     * 注册用户表服务层对象
     */
    @Resource(name = "cusInfoServiceImpl")
    private CusInfoService cusInfoService;

    private static final Logger LOG = LoggerFactory.getLogger(TestPrintServiceImpl.class);



    public void execute() {
        LOG.info("test方法开始执行");
//        Date date = new Date();
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(date);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        calendar.set(Calendar.MILLISECOND, 0);
//        Date nowDate = calendar.getTime();
        System.out.println("in test execute");

//        CusInfoVo info = cusInfoService.getCusInfoById((long)42329);
//        System.out.println(info);

        LOG.info("test方法执行完毕");
    }

}
