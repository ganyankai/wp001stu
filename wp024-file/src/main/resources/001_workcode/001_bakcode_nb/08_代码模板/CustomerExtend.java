package com.zrytech.framework.newshop.entity.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zrytech.framework.base.entity.BaseEntity;
import com.zrytech.framework.common.util.DictionUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "customer_extend")
@ApiModel(value = "客户扩展entry")
public class CustomerExtend extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 54835311354876L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "主键id", required = false)
    private Integer id;

    @Column(name = "name")
    @ApiModelProperty(value = "昵称", required = false)
    private String name;

    @Column(name = "logo")
    @ApiModelProperty(value = "头像", required = false)
    private String logo;

    @Column(name = "tel")
    @ApiModelProperty(value = "电话", required = false)
    private String tel;

    @Column(name = "parent_code")
    @ApiModelProperty(value = "邀请码", required = false)
    private String inviteCode;

    @Column(name = "self_code")
    @ApiModelProperty(value = "随机码", required = false)
    private String randomCode;

    @Column(name = "status")
    @ApiModelProperty(value = "状态", required = false)
    private String status;

    @ApiModelProperty(value = "状态", required = false)
    @Transient
    private String statusCN;

    @Column(name = "level_name")
    @ApiModelProperty(value = "vip类别", required = false)
    private String level;

    @Column(name = "level_index")
    private Integer levelIndex;

    @Transient
    private String levelCN;

    @Column(name = "is_vip")
    @ApiModelProperty(value = "是否为vip", required = false)
    private Boolean isVip;

    @Column(name = "active_type")
    @ApiModelProperty(value = "激活类型(卡激活，线上购买)", required = false)
    private String activeType;

    @Transient
    private String activeTypeCN;

    @Column(name = "direct_one_count")
    @ApiModelProperty(value = "一级邀请人数", required = false)
    private Integer directOneCount;

    @Column(name = "direct_two_count")
    @ApiModelProperty(value = "二级邀请人数", required = false)
    private Integer directTwoCount;

    @Column(name = "today_money")
    @ApiModelProperty(value = "我的今日资产", required = false)
    private BigDecimal todayMoney;

    @Column(name = "yestoday_money")
    @ApiModelProperty(value = "我的昨日资产", required = false)
    private BigDecimal yestodayMoney;

    @Column(name = "month_money")
    @ApiModelProperty(value = "我的本月资产", required = false)
    private BigDecimal monthMoney;

    @Column(name = "all_money")
    @ApiModelProperty(value = "我的所有资产", required = false)
    private BigDecimal allMoney;

    @Column(name = "balance")
    @ApiModelProperty(value = "可提现余额", required = false)
    private BigDecimal balance;

    @Column(name = "vip_count")
    @ApiModelProperty(value = "已邀会员", required = false)
    private Integer vipCount;

    @Column(name = "fans_count")
    @ApiModelProperty(value = "已邀粉丝", required = false)
    private Integer fansCount;

    @Column(name = "customer_id")
    @ApiModelProperty(value = "客户ID", required = false)
    private Integer customerId;

    @Column(name = "age")
    @ApiModelProperty(value = "年龄", required = false)
    private Integer age;

    @Column(name = "birthday")
    @ApiModelProperty(value = "生日", required = false)
    private String birthday;

    @Column(name = "audit")
    @ApiModelProperty(value = "课程审核", required = false)
    private Boolean audit;

    @Column(name = "gender")
    @ApiModelProperty(value = "性别", required = false)
    private Integer gender;

    @Column(name = "extension")
    @ApiModelProperty(value = "扩展字段", required = false)
    private String extension;

    @Column(name = "qr_img_url")
    @ApiModelProperty(value = "二维码路径", required = false)
    public String qrImgUrl;

    /**
     * 完成的销售任务数
     **/
    @Column(name = "selar_tasks")
    private BigDecimal selarTasks;


    /**
     * 积分
     **/
    @Column(name = "score")
    private Integer score;

    @Transient
    @ApiModelProperty(value = "验证码", required = false)
    private String code;

    @Transient
    private String password;

    @Transient
    private String confirmPassword;

    @Transient
    private String oldPassword;

    @Transient
    private String genderCN;

    @Transient
    private String cardNo;

    @Transient
    private Integer teamNum;

    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "过期时间", required = false)
    private Date expiredTime;

    @Column(name = "active_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "激活日期", required = false)
    private Date activeTime;

    @Column(name = "team_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "加入团队时间", required = false)
    private Date teamDate;

    /**
     *  分享数
     **/
    @Column(name = "share_qty")
    private Integer shareQty;

    /**
     *  查看数
     **/
    @Column(name = "view_qty")
    private Integer viewQty;

    /**
     *  标签
     **/
    @Column(name = "label")
    private String label;

    /**
     *  客服id
     **/
    @Column(name = "user_service_id")
    private Integer userServiceId;

    @Transient
    private String userServiceName;

//    --imp
    public String getLabelCN() {
        if (!StringUtils.isEmpty(label)) {
//            return DictionUtil.getValue(LabelContant.LABEL, label);
        }
        return label;
    }

//    public String getStatusCN() {
//        if (!StringUtils.isEmpty(status)) {
//            return DictionaryUtil.getValue(CourseContant.ACTIVE_STATUS, status);
//        }
//        return statusCN;
//    }
//
//    public String getLevelCN() {
//        if (!StringUtils.isEmpty(level)) {
//            return DictionaryUtil.getValue(CourseContant.VIP_TYPE, level);
//        }
//        return levelCN;
//    }
//
//  /*  public Date getExpiredTime() {
//        if (!StringUtils.isEmpty(activeTime)) {
//            return TimeUtil.getDate(activeTime);
//        }
//        return expiredTime;
//    }*/
//
//    @Transient
//    private int progressBar;
//    @Transient
//    private String nextLevelCN;
//
//    public String getNextLevelCN() {
//        if (!StringUtils.isEmpty(level)) {
//            if (level.equals(CourseContant.ORDINARY_CUSTOMER)) {
//                return DictionaryUtil.getValue(CourseContant.VIP_TYPE, CourseContant.SALER_CUSTOMER);
//            }
//            if (level.equals(CourseContant.SALER_CUSTOMER)) {
//                return DictionaryUtil.getValue(CourseContant.VIP_TYPE, CourseContant.PARTNER_CUSTOMER);
//            }
//        }
//        return levelCN;
//    }
//
//    public Integer getProgressBar() {
//        if (!StringUtils.isEmpty(level)) {
//            if (level.equals(CourseContant.ORDINARY_CUSTOMER)) {
//                return vipCount * 100 / CourseContant.TO_SALER_CUSTOMER;
//            }
//            if (level.equals(CourseContant.SALER_CUSTOMER)) {
//                return vipCount * 100 / CourseContant.TO_PARTNER_CUSTOMER;
//            }
//        }
//        return progressBar;
//    }
//
//
//    public String getActiveTypeCN() {
//        if (!StringUtils.isEmpty(activeType)) {
//            return DictionaryUtil.getValue(CourseContant.ACTIVE_TYPE, activeType);
//        }
//        return activeTypeCN;
//    }

    public String getGenderCN() {
        if (gender != null && gender == 0) {
            return "女";
        } else if (gender != null && gender == 1) {
            return "男";
        } else {
            return null;
        }
    }

    //查询字段;
    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "激活日期", required = false)
    public Date startDate;


    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "激活日期", required = false)
    public Date endDate;

    @Transient
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "注册时间", required = false)
    public Date createDate;


    /**
     * 渠道编号
     */
    @Column(name = "agent_id")
    private Integer agentId;



    /**
     * 是否有拿伯乐奖资格
     **/
    @Transient
    private Boolean earnParlexReward;

    public CustomerExtend() {
        this.earnParlexReward = false;
        this.directOneCount=0;
        this.directTwoCount=0;
    }
}
