package com.catt.oil.repository.entity.lockPriceMgr;

import com.catt.oil.repository.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Table(name = "t_lock_oilstation")
@Data
@Entity
public class LockOilstation extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "S_ID")
    private Long id;

    //锁价ID
    @Column(name = "LOCK_PRICE_ID")
    private Long lockPriceId;

    //所属油站ID
    @Column(name = "STATION_ID")
    private Long stationId;

    @Column(name="D_CREATE_DATE")
    private Date createDate;

    @Column(name="D_MODIFY_DATE")
    private Date modifyDate;

}
