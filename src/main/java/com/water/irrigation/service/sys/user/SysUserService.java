package com.water.irrigation.service.sys.user;


import com.water.irrigation.entity.dto.sys.user.SysUserDto;
import com.water.irrigation.entity.sys.user.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SysUserService {

    SysUser findUserByUsername(String username);

    boolean checkNameIsExistOrNot(String username);

    SysUser saveUser(SysUser sysUser);

    /**
     * 根据条件获取对应的资产信息数据
     *
     * @param conditions 资产信息过滤条件
     * @param pageable   分页
     * @return 指定的资产信息列表
     */
    Page<SysUser> findAll(SysUserDto conditions, Pageable pageable);

    SysUser update(SysUser assetInfo);

    void delete(List<SysUser> sysCompanys);

    /**
     * 获取当前登录用户
     * @return
     */
    SysUser getUser();
}


