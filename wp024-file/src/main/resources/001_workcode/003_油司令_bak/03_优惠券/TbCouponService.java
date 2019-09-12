package com.catt.oil.service.coupon;

import com.catt.common.base.pojo.search.Page;
import com.catt.common.base.pojo.search.Pageable;
import com.catt.common.base.service.BaseService;
import com.catt.oil.repository.entity.coupon.TbCoupon;
import com.catt.oil.repository.form.coupon.TbCouponForm;

import java.util.List;
import java.util.Map;

/**
 * 优惠券管理接口
 */
public interface TbCouponService extends BaseService<TbCoupon, Long> {
    Page<Map> tbCouponList(TbCouponForm tbCouponForm, Pageable pageable);

    void updateTbCoupon(TbCouponForm tbCouponForm);

    void addTbCoupon(TbCouponForm tbCouponForm);

    @Override
    TbCoupon find(Long id);

    Page<Map> receiveRecord(TbCouponForm tbCouponForm,Pageable pageable);


}
