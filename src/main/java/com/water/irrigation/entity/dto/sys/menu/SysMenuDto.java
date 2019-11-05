package com.water.irrigation.entity.dto.sys.menu;

import com.water.irrigation.entity.sys.menu.SysMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenuDto extends SysMenu {

    /**
     * 页数
     */
    private Integer pageno = 1;

    /**
     * 每页记录数
     */
    private Integer pagesize = 10;

    /**
     * 需要删除的数据列表
     */
    private List<SysMenu> delLists;
}
