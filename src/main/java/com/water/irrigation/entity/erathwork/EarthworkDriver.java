package com.water.irrigation.entity.erathwork;

import com.water.irrigation.entity.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;

@Data
@Table(name="earthwork_driver")
@Entity
public class EarthworkDriver extends BaseEntity {

    private String scard;
    private String sname;
    private String phone;
    private Integer itype;

}
