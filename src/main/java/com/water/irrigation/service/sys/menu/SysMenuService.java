package com.water.irrigation.service.sys.menu;

import com.water.irrigation.entity.dto.sys.menu.SysMenuDto;
import com.water.irrigation.entity.sys.menu.SysMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SysMenuService {

    List<SysMenu> findByLevel(Integer level);

    Page<SysMenu> findAll(SysMenuDto conditions, Pageable pageable);

    SysMenu add(SysMenu sysMenu);

    SysMenu update(SysMenu sysMenu);

    void delete(List<SysMenu> sysMenus);

    SysMenu findById(Long id);
}
