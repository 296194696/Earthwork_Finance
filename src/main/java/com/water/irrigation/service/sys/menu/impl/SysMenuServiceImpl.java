package com.water.irrigation.service.sys.menu.impl;

import com.water.irrigation.dao.sys.menu.SysMenuDao;
import com.water.irrigation.entity.dto.sys.menu.SysMenuDto;
import com.water.irrigation.entity.sys.menu.SysMenu;
import com.water.irrigation.service.sys.menu.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuDao sysMenuDao;

    /**
     * 根据条件获取对应的资产信息数据
     *
     * @param conditions 资产信息过滤条件
     * @param pageable   分页
     * @return 指定的资产信息列表
     */
    @Override
    public Page<SysMenu> findAll(SysMenuDto conditions, Pageable pageable) {
        return sysMenuDao.findAll(getSpecification(conditions), pageable);
    }

    /**
     * 根据主键查找
     *
     * @param id 主键
     * @return java.util.List<SysMenu>
     * @author licl
     */
    public SysMenu findById(Long id){
        return sysMenuDao.findById(id).get();
    }

    /**
     * 根据层级查找
     *
     * @param level 层级
     * @return java.util.List<SysMenu>
     * @author licl
     */
    public List<SysMenu> findByLevel(Integer level){
        return sysMenuDao.findByLevel(level);
    }

    /**
     * 添加菜单组织
     * @param sysMenu 要添加的菜单组织信息
     * @return 添加的菜单组织信息
     */
    @Override
    public SysMenu add(SysMenu sysMenu) {
        return sysMenuDao.save(sysMenu);
    }

    /**
     * 修改菜单组织
     * @param sysMenu 菜单组织信息
     * @return  更新后菜单组织信息
     */
    @Override
    public SysMenu update(SysMenu sysMenu) {
        SysMenu orgObj =
                this.sysMenuDao.findById(sysMenu.getIndocno()).get();
        if(orgObj!=null) {
            System.out.println("无效的修改对象");
        }
        //UpdateHelperUtils.copyNullProperties(orgObj, SysMenu);
        return sysMenuDao.save(sysMenu);
    }

    /**
     * 删除菜单组织信息
     * @param sysMenus 需要删除的资产信息
     */
    @Override
    public void delete(List<SysMenu> sysMenus) {
        for(SysMenu sysMenu : sysMenus ) {
            sysMenuDao.deleteById(sysMenu.getIndocno());
        }
    }

    /**
     * 查询条件构建
     * @param condition
     * @return 查询条件
     */
    private Specification<SysMenu> getSpecification(SysMenuDto condition){
        // 查询条件构造
        Specification<SysMenu> spec = new Specification<SysMenu>(){

            private static final long serialVersionUID = 3652606507255419478L;

            @Override
            public Predicate toPredicate(Root<SysMenu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> listPredicates = new ArrayList<Predicate>();
//                Join<SysMenu, SysMenu> join = root.join("parent", JoinType.INNER);
                if (condition.getLevel()!=null && !"".equals(condition.getLevel())) {
                    listPredicates.add(cb.like(root.get("level").as(String.class),
                            "%"+condition.getLevel()+"%"));
                }
                if (condition.getIroleid()!=null && !"".equals(condition.getIroleid())) {
                    listPredicates.add(cb.equal(root.get("iroleid").as(Integer.class),
                            condition.getIroleid()));
//                    listPredicates.add(cb.equal(root.<SysMenu>get("parent").get("iroleid").as(Integer.class),
//                            condition.getIroleid()));
                }
                Predicate[] arrayPredicates = new Predicate[listPredicates.size()];
                Predicate p = cb.and(listPredicates.toArray(arrayPredicates));
                return p;
            }
        };
        return spec;
    }
}
