package com.catt.oil.repository.form.lockPrice;

import com.catt.oil.repository.entity.base.BaseEntity;
import com.catt.oil.repository.entity.oilStationMgr.OilstationInfo;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class LockPriceForm implements Serializable {
    private static final long serialVersionUID = 1L;

//    private Long id;
    private String id;

    //所属集团/区域ID
    private String groupId;

    //油品类型（字典表）
    private Integer productKind;

    //价格优惠幅度（价格锁死）
    private BigDecimal deathRange;

    //价格优惠幅度（跟跌不跟涨）
    private BigDecimal floatRange;

    //状态：启动/禁用（字典表）
    private Integer status;

    private Date createDate;

    private Date modifyDate;

    private List<OilstationInfo> list;

    private String oids;

    private String ids;
}
