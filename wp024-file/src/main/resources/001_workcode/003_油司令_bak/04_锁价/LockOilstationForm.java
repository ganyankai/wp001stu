package com.catt.oil.repository.form.lockPrice;

import com.catt.oil.repository.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
public class LockOilstationForm extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    //锁价ID
    private Long lockPriceId;

    //所属油站ID
    private Long stationId;

    private Date createDate;

    private Date modifyDate;

}
