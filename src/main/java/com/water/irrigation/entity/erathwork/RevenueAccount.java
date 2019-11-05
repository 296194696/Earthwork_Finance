package com.water.irrigation.entity.erathwork;

import com.water.irrigation.entity.base.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Table(name="earthwork_revenue_account")
@Entity
public class RevenueAccount extends BaseEntity {

    private String sname;
}
