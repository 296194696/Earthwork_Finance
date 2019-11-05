package com.water.irrigation.dao.sys.user;

import com.water.irrigation.entity.sys.user.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


/**
 * 登录用户信息表
 * @author lichunlei
 *
 */
public interface SysUserDao extends JpaRepository<SysUser,Long>, JpaSpecificationExecutor<SysUser> {
    SysUser findByUsername(String username);
    SysUser findByPassword(String password);
}
