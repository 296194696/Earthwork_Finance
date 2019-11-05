package com.water.irrigation.entity.sys.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sys_user")
@Data
public class SysUser {

    @Id
    @GeneratedValue
    private Long indocno;
    private String username;
    private String password;
    private String role;
    private String rolename;
    private Integer iroleid;
    private String sname;
    //映射单向一对多的关联关系
    //使用@OneToMany 来映射一对多的关联关系
    //使用@JoinColumn 来映射外键列的名称
    //可以使用@OneToMany的fetch 属性来修改默认的加载策略
    //可以通过@OneToMany的cascade 属性来修改默认的删除策略
    //cascade={CascadeType.REMOVE} 会把主表和从表的数据都删除
    //mappedBy表明放弃关联关系维护并且不能在使用
    //注意：若在一的一端@oneToMany 中使用mapperBy属性，则@oneToMany端就不能在使用@JoinColumn(name="CUSTOMER_ID")属性
//    @JoinColumn(name="CUSTOMER_ID")
//    @OneToMany(fetch=FetchType.EAGER,cascade={CascadeType.REMOVE},mappedBy="sysUser")
//    private List<SysRole> sysRoles=new ArrayList<SysRole>();
//    @JsonIgnore//防止死循环，导致栈溢出
//    @OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.REMOVE},mappedBy="sysUser")
//    @Fetch(FetchMode.SUBSELECT)
//    private List<BlockInfo> blockInfos=new ArrayList<BlockInfo>();
//    @OneToMany(fetch=FetchType.LAZY,cascade={CascadeType.REMOVE},mappedBy="sysUserCharge")
//    @Fetch(FetchMode.SUBSELECT)
//    private List<Charge> charges=new ArrayList<Charge>();

}
