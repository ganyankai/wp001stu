package com.zrytech.framework.app.constants;

public class CustomConstants {

    /**
     * 渠道商品状态
     */
    public static final String AGENT_PDT_STATUS = "agent_product_status";

    /**
     * 渠道商品状态：上架
     */
    public static final String AGENT_PDT_STATUS_UP = "agent_product_status_up";

    /**
     * 渠道商品状态：下架
     */
    public static final String AGENT_PDT_STATUS_DOWN = "agent_product_status_down";

    /**
     * 渠道商品状态：平台商品下架
     */
    public static final String AGENT_PDT_STATUS_PRODUCT_DOWN = "agent_product_status_product_down";

    /**
     * 车辆状态：正则
     */
    public static final String AGENT_PDT_STATUS_REG_EX = "^(" + AGENT_PDT_STATUS_UP + ")|(" + AGENT_PDT_STATUS_DOWN
            + ")|()$";

    public static final String AGENT_PDT_STATUS_ERR_MSG = "渠道商品状态有误";


    /**
     * 购物车商品类型
     */
    public static final String SHOP_CAR_PDT_TYPE = "shop_car_product_type";
    /**
     * 购物车商品类型：基本商品
     */
    public static final String SHOP_CAR_PDT_TYPE_BASE = "shop_car_product_type_base";
    /**
     * 购物车商品类型：秒杀商品
     */
    public static final String SHOP_CAR_PDT_TYPE_SECKILL = "shop_car_product_type_seckill";
    /**
     * 购物车商品类型：众筹商品
     */
    public static final String SHOP_CAR_PDT_TYPE_CROWD = "shop_car_product_type_crowd";
    /**
     * 购物车商品类型：团购商品
     */
    public static final String SHOP_CAR_PDT_TYPE_GROUP = "shop_car_product_type_group";
    /**
     * 购物车商品类型：砍价商品
     */
    public static final String SHOP_CAR_PDT_TYPE_BARGAIN = "shop_car_product_type_bargain";
    /**
     * 购物车商品类型：正则
     */
    public static final String SHOP_CAR_PDT_TYPE_REG_EX = "^(" + SHOP_CAR_PDT_TYPE_BASE + ")|("
            + SHOP_CAR_PDT_TYPE_SECKILL + ")|()$";
    public static final String SHOP_CAR_PDT_TYPE_ERR_MSG = "购物车商品类型有误";


    /**
     * 渠道商品是在主页是否显示
     */
    public static final String AGENT_PRODUCT_IS_DISPLAY = "agent_product_is_display";
    /**
     * 渠道商品是不在主页显示
     */
    public static final String AGENT_PRODUCT_IS_DISPLAY_NO = "agent_product_is_display_no";
    /**
     * 渠道商品是在主页显示
     */
    public static final String AGENT_PRODUCT_IS_DISPLAY_YES = "agent_product_is_display_yes";


    /**
     * 订单状态
     */
    public static final String INDENT_STATUS = "indent_status";
    /**
     * 订单状态：待支付
     */
    public static final String INDENT_STATUS_WAIT_PAY = "indent_status_wait_pay";
    /**
     * 订单状态：待安装
     */
    public static final String INDENT_STATUS_WAIT_INSTALL = "indent_status_wait_install";
    /**
     * 订单状态：待评价
     */
    public static final String INDENT_STATUS_WAIT_COMMENT = "indent_status_wait_comment";
    ///** 订单状态：售后/退款 */
    //public static final String INDENT_STATUS_AFTER_SALE_SERVICE = "indent_status_after_sale_service";
    /**
     * 订单状态：待退款
     */
    public static final String INDENT_STATUS_WAIT_REFUND = "indent_status_wait_refund";
    /**
     * 订单状态：已退款
     */
    public static final String INDENT_STATUS_REFUNDED = "indent_status_refunded";
    /**
     * 订单状态：已完成
     */
    public static final String INDENT_STATUS_COMPLETED = "indent_status_completed";
    /**
     * 订单状态：已取消
     */
    public static final String INDENT_STATUS_CANCELDE = "indent_status_canceled";
    /**
     * 订单状态：团购中
     */
    public static final String INDENT_STATUS_IN_GROUP = "indent_status_in_group";
    /**
     * 订单状态：众筹中
     */
    public static final String INDENT_STATUS_IN_CROWD = "indent_status_in_crowd";
    /**
     * 订单状态：正则
     */
    public static final String INDENT_STATUS_REG_EX = "^(" + INDENT_STATUS_WAIT_PAY + ")|(" + INDENT_STATUS_WAIT_INSTALL
            + ")|(" + INDENT_STATUS_WAIT_COMMENT + ")|(" + INDENT_STATUS_WAIT_REFUND + ")|(" + INDENT_STATUS_REFUNDED
            + ")|(" + INDENT_STATUS_COMPLETED + ")|(" + INDENT_STATUS_CANCELDE + ")|(" + INDENT_STATUS_IN_GROUP + ")|("
            + INDENT_STATUS_IN_CROWD + ")|()$";
    public static final String INDENT_STATUS_ERR_MSG = "参数有误：订单状态有误";


    /**
     * 订单类型
     */
    public static final String INDENT_TYPE = "indent_type";
    /**
     * 订单类型：普通订单
     */
    public static final String INDENT_TYPE_BASE = "indent_type_base";
    /**
     * 订单类型：秒杀订单
     */
    public static final String INDENT_TYPE_SECKILL = "product_type_seckill";
    /**
     * 订单类型：众筹订单
     */
    public static final String INDENT_TYPE_CROWD = "product_type_crowd";
    /**
     * 订单类型：砍价订单
     */
    public static final String INDENT_TYPE_BARGAIN = "product_type_bargain";
    /**
     * 订单类型：团购订单
     */
    public static final String INDENT_TYPE_GROUP = "product_type_group";
    /**
     * 订单类型：正则
     */
    public static final String INDENT_TYPE_REG_EX = "^(" + INDENT_TYPE_BASE + ")|(" + INDENT_TYPE_SECKILL + ")|("
            + INDENT_TYPE_CROWD + ")|(" + INDENT_TYPE_BARGAIN + ")|(" + INDENT_TYPE_GROUP + ")|()$";
    public static final String INDENT_TYPE_ERR_MSG = "参数有误：订单类型有误";


    /**
     * 活动商品状态
     */
    public static final String ACTIVITY_PRODUCT_SATATUS = "activity_product_satatus";
    /**
     * 未上架
     */
    public static final String ACTIVITY_PRODUCT_SATATUS_NOTSTARTED = "activity_product_satatus_notstarted";

    /**
     * 上架
     */
    public static final String ACTIVITY_PRODUCT_SATATUS_UP = "activity_product_satatus_up";


    // 未开始
    public static final String ACTIVITY_PRODUCT_STATUS_UNFINISHED = "activity_unfinished";

    // 進行中
    public static final String ACTIVITY_PRODUCT_STATUS_CONDUCT = "activity_product_middle";

    // 已完成
    public static final String ACTIVITY_PRODUCT_STATUS_COMPLETED = "activity_product_end";



    // 已过期
    public static final String ACTIVITY_PRODUCT_STATUS_OVERDUE = "activity_product_overdue";



    /**
     * 活动状态
     */
    public static final String ACTIVITY_STATUS = "activity_status";

    //已完成
    public static final String ACTIVITY__STATUS_COMPLETED = "activity_end";

    // 进行中
    public static final String ACTIVITY_STATUS_CONDUCT = "activity_middle";

    // 报名中
    public static final String ACTIVITY_STATUS_UNFINISHED = "activity_unfinished";

    /**
     * 客户标签
     */
    public static final String LABEL = "label";

     //有购买意向
    public static final String LABEL_HAS = "has";

    //无购买意向
    public static final String LABEL_NO = "no";

}
