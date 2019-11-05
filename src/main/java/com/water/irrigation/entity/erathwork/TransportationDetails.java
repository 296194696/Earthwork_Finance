package com.water.irrigation.entity.erathwork;

import com.water.irrigation.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

@Data
@Table(name="earthwork_transportation_details")
@Entity
public class TransportationDetails extends BaseEntity {

    private String scard;
    private String sname;
    private String placeStart;
    private String placeEnd;
    private Double kilometres;
    private Double scost;
    private Double itrip;
    private Double unitprice;
    private Double stotal;
    private Date date;


}
