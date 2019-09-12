package com.catt.oil.repository.entity.lockPriceMgr;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.catt.common.base.repository.entity.UUIDBaseEntity;
import com.catt.oil.repository.entity.oilStationMgr.OilstationInfo;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Data;

@Data
@Entity
//@JsonAutoDetect
@Table(name = "T_LOCK_PRICE")
public class LockPrice extends UUIDBaseEntity {
    private static final long serialVersionUID = 1L;


    //所属集团/区域ID
    @Column(name = "GROUP_ID")
    private String groupId;

    //油品类型（字典表）
    @Column(name = "PRODUCT_KIND")
    private Integer productKind;

    //价格优惠幅度（价格锁死）
    @Column(name = "DEATH_RANGE")
    private BigDecimal deathRange;

    //价格优惠幅度（跟跌不跟涨）
    @Column(name = "FLOAT_RANGE")
    private BigDecimal floatRange;

    //状态：启动/禁用（字典表）
    @Column(name = "STATUS")
    private Integer status;

//    @Transient
//    private List<OilstationInfo> list;

//    @Transient
//    private String oids;
}
