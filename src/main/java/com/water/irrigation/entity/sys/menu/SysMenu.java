package com.water.irrigation.entity.sys.menu;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sys_menu")
@Data
public class SysMenu {

    /**
     * 菜单ID
     */
    @Id
    @GeneratedValue
    private Long indocno;
    /**
     * 父级菜单ID
     */
    private Long parent_id;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 菜单类型：top_menu, main_menu, admin_menu
     */
    private String type;
    /**
     * 菜单URL
     */
    private String url;
    /**
     * 菜单图标
     */
    private String icon;
    /**
     * 菜单分数，越大排序越前
     */
    private Integer score;

    /**
     * 层级
     */
    private Integer level;
    /**
     * 角色ID
     */
    private Integer iroleid;

    // 父节点
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinColumn(name = "parent_id",insertable = false,updatable = false)
//    private SysMenu parent;
    @JsonIgnore//防止死循环，导致栈溢出
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="parent_id",updatable = false,insertable = false)
    private SysMenu parent;

    /**
     * 子类
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy="parent")
//    @JoinColumn(name = "parent_id")
    @OrderBy("score asc")
    private List<SysMenu> children = new ArrayList<SysMenu>();

}
