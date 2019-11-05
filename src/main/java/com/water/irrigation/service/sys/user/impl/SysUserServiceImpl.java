package com.water.irrigation.service.sys.user.impl;

import com.water.irrigation.dao.sys.user.SysUserDao;
import com.water.irrigation.entity.dto.sys.user.SysUserDto;
import com.water.irrigation.entity.sys.user.SysUser;
import com.water.irrigation.service.sys.user.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserDao sysUserDao;


    @Override
    public SysUser findUserByUsername(String username) {
        return sysUserDao.findByUsername(username);
    }
    @Override
    public boolean checkNameIsExistOrNot(String username){
        SysUser sysUser=sysUserDao.findByUsername(username);
        if(null==sysUser){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public SysUser saveUser(SysUser sysUser){
        return sysUserDao.save(sysUser);
    }

    @Override
    public SysUser getUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        String username=userDetails.getUsername();
        SysUser sysUser=sysUserDao.findByUsername(username);
        return sysUser;
    }
    /**
     * 根据条件获取对应的用户信息数据
     *
     * @param conditions 用户信息过滤条件
     * @param pageable   分页
     * @return 指定的用户信息列表
     */
    @Override
    public Page<SysUser> findAll(SysUserDto conditions, Pageable pageable) {
        return sysUserDao.findAll(getSpecification(conditions), pageable);
    }
    
    /**
     * 修改用户
     * @param SysUser 用户信息
     * @return  更新后用户信息
     */
    @Override
    public SysUser update(SysUser SysUser) {
        SysUser orgObj =
                this.sysUserDao.findById(SysUser.getIndocno()).get();
        if(orgObj!=null) {
            System.out.println("无效的修改对象");
        }
        //UpdateHelperUtils.copyNullProperties(orgObj, SysUser);
        return sysUserDao.save(SysUser);
    }

    /**
     * 删除用户信息
     * @param sysCompanys 需要删除的用户信息
     */
    @Override
    public void delete(List<SysUser> sysCompanys) {
        for(SysUser SysUser : sysCompanys ) {
            sysUserDao.deleteById(SysUser.getIndocno());
        }
    }

    /**
     * 查询条件构建
     * @param condition
     * @return 查询条件
     */
    private Specification<SysUser> getSpecification(SysUserDto condition){
        // 查询条件构造
        Specification<SysUser> spec = new Specification<SysUser>(){

            private static final long serialVersionUID = 3652606507255419478L;

            @Override
            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> listPredicates = new ArrayList<Predicate>();
                if (condition.getUsername()!=null && !"".equals(condition.getUsername())) {
                    listPredicates.add(cb.like(root.get("username").as(String.class),
                            "%"+condition.getUsername()+"%"));
                }
                if (condition.getIroleid()!=null && !"".equals(condition.getIroleid())) {
                    listPredicates.add(cb.equal(root.get("iroleid").as(Integer.class),
                            condition.getIroleid()));
                }
                if (condition.getSname()!=null && !"".equals(condition.getSname())) {
                    listPredicates.add(cb.like(root.get("sname").as(String.class),
                            "%"+condition.getSname()+"%"));
                }
                Predicate[] arrayPredicates = new Predicate[listPredicates.size()];
                Predicate p = cb.and(listPredicates.toArray(arrayPredicates));
                return p;
            }
        };
        return spec;
    }

}
