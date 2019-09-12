package com.zrytech.framework.newshop.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单统计dto
 */
@Data
public class IndentStaVo {
    //销售额
    private BigDecimal saleTotal;
    //待处理订单
    private Integer todoIndent;
    //新增会员
    private Integer newCustomer;
    //订单销售总数量
    private Integer saleCount;
    //订单成交数量
    private Integer indentCount;

    //订单成交比例
    private BigDecimal indentPercentage;

}
