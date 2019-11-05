package com.water.irrigation.entity.sys.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "sys_role")
@Data
public class SysRole {

    @Id
    @GeneratedValue
    private Long indocno;
    @Column(insertable = false,updatable = false)
    private Long iuserid;
    private String srolename;
    private String sdescription;
    //映射单向n-1的关联关系
    //使用@ManyToOne 来映射多对一的关系
    //使用@JoinColumn 来映射外键
    //可以使用@ManyToOne的fetch属性来修改默认的关联属性的加载策略
//    @JsonIgnore//防止死循环，导致栈溢出
//    @JoinColumn(name="iuserid")//外键列的列名
//    @ManyToOne(fetch=FetchType.LAZY)
//    private SysUser sysUser;


}
