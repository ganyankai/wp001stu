package com.catt.oil.repository.form.coupon;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class TbCouponForm implements Serializable {
    /**
     * <pre>
     * Id
     * </pre>
     */
    private Long id;

    //创建时间

    private Date createDate = new Date();

    //过期时间
    private Date expirationDate;

    //生效时间
    private Date startDate;

    //归属人
    private Integer createBy;

    //状态
    private String state;

    //优惠券来源
    private String couponSource;

    //优惠劵类型
    private String couponType;

    //备注
    private String remark;

    //使用条件
    private BigDecimal couponCondition;

    //优惠金额
    private BigDecimal couponValue;

    //剩余数量
    private Integer remainQty;

    //使用数量
    private Integer useQty;

    //发行数量
    private Integer publishQty;

    //设备类型
//    private String deviceType;

    private Date startTime;
//    private String startTime;

    private Date endTime;
//    private String endTime;
}
