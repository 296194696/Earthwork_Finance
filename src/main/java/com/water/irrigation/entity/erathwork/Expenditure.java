package com.water.irrigation.entity.erathwork;

import com.water.irrigation.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

@Data
@Table(name="earthwork_expenditure")
@Entity
public class Expenditure extends BaseEntity {

    private String sremarks;
    private String saccount;
    private String srevenue;
    private Double amount;
    private Date date;
    private Long itranid;

}
