package com.catt.oil.web.controller.admin.coupon;

import com.catt.common.base.pojo.search.Page;
import com.catt.common.base.pojo.search.Pageable;
import com.catt.common.util.collections.CollectionUtil;
import com.catt.common.util.lang.StringUtil;
import com.catt.common.web.Message;
import com.catt.common.web.controller.BaseController;
import com.catt.oil.repository.data.CusInfoVo;
import com.catt.oil.repository.entity.coupon.TbCoupon;
import com.catt.oil.repository.form.coupon.TbCouponForm;
import com.catt.oil.repository.form.customerMgr.CusInfoForm;
import com.catt.oil.service.coupon.TbCouponService;
import com.catt.oil.service.customerMgr.CusInfoService;
import com.catt.oil.service.customerMgr.CusOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import util.CryptUtil;
import util.MaskUtil;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Pachage: com.catt.oil.web.controller.admin.customerMgr.customerQuery
 * Description:注册用户Controller
 * date: 2015-11-19 下午5:31
 * author: Zhou mingxiang
 */
@RequestMapping({"/admin/tbcoupon"})
@Controller("admin.tbcoupon")
public class TbCouponController extends BaseController {
    
    /**
     *
     */
    @Resource(name = "tbCouponServiceImpl")
    private TbCouponService tbCouponService;

    /**
     *
     * @param tbCouponForm
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value = {"/tbCouponList"}, method = RequestMethod.POST)
    @ResponseBody
    public Page<Map> tbCouponList(TbCouponForm tbCouponForm, Integer pageNo, Integer pageSize) {
        Pageable pageable = new Pageable(pageNo, pageSize);
//        Page<Map> page = cusInfoService.getCusInfoByList(cusInfoForm, pageable);
        Page<Map> page = tbCouponService.tbCouponList(tbCouponForm, pageable);
        return page;
    }

    /**
     * 优惠券修改
     */
    @RequestMapping(value = {"/updateTbCoupon"}, method = RequestMethod.POST)
    @ResponseBody
    public Message updateTbCoupon(TbCouponForm tbCouponForm){
        tbCouponService.updateTbCoupon(tbCouponForm);

        return SUCCESS_MSG;
    }

    /**
     * 优惠券新增
     */
    @RequestMapping(value = {"/addTbCoupon"}, method = RequestMethod.POST)
    @ResponseBody
    public Message addTbCoupon(TbCouponForm tbCouponForm){
        tbCouponService.addTbCoupon(tbCouponForm);

        return SUCCESS_MSG;
    }

}
