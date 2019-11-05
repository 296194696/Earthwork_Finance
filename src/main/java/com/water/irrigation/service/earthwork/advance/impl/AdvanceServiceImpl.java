package com.water.irrigation.service.earthwork.advance.impl;


import com.water.irrigation.dao.earthwork.AdvanceDao;
import com.water.irrigation.entity.dto.erathwork.AdvanceDto;
import com.water.irrigation.entity.erathwork.Advance;
import com.water.irrigation.service.earthwork.advance.AdvanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


/**
 * 资产信息服务层
 * @author lichunlei
 *
 */
@Service
public class AdvanceServiceImpl implements AdvanceService {

    @Autowired
    private AdvanceDao advanceDao;

    /**
     * 根据条件获取对应的资产信息数据
     *
     * @param conditions 资产信息过滤条件
     * @param pageable   分页
     * @return 指定的资产信息列表
     */
    @Override
    public Page<Advance> findAll(AdvanceDto conditions, Pageable pageable) {
        return advanceDao.findAll(getSpecification(conditions), pageable);
    }

    /**
     * 根据编号获取对应的资产数据
     * @param indocno 主键
     * @return 指定的资产信息
     */
    @Override
    public Advance findOne(Long indocno) {
        return advanceDao.findById(indocno).get();
    }

    /**
     * 添加资产
     * @param advance 要添加的资产信息
     * @return 添加的资产信息
     */
    @Override
    public Advance add(Advance advance) {
        return advanceDao.save(advance);
    }

    /**
     * 修改资产
     * @param advance 资产信息
     * @return  更新后资产信息
     */
    @Override
    public Advance update(Advance advance) {
        Advance orgObj =
                this.advanceDao.findById(advance.getIndocno()).get();
        if(orgObj!=null) {
            System.out.println("无效的修改对象");
        }
        //UpdateHelperUtils.copyNullProperties(orgObj, Customer);
        return advanceDao.save(advance);
    }

    /**
     * 删除资产信息
     * @param sysCompanys 需要删除的资产信息
     */
    @Override
    public void delete(List<Advance> sysCompanys) {
        for(Advance advance : sysCompanys ) {
            advanceDao.deleteById(advance.getIndocno());
        }
    }

    /**
     * 查询条件构建
     * @param condition
     * @return 查询条件
     */
    private Specification<Advance> getSpecification(AdvanceDto condition){
        // 查询条件构造
        Specification<Advance> spec = new Specification<Advance>(){

            private static final long serialVersionUID = 3652606507255419478L;

            @Override
            public Predicate toPredicate(Root<Advance> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> listPredicates = new ArrayList<Predicate>();
                if (condition.getSaccount()!=null && !"".equals(condition.getSaccount())) {
                    listPredicates.add(cb.like(root.get("saccount").as(String.class),
                            "%"+condition.getSaccount()+"%"));
                }
                if (condition.getSrevenue()!=null && !"".equals(condition.getSrevenue())) {
                    listPredicates.add(cb.like(root.get("srevenue").as(String.class),
                            "%"+condition.getSrevenue()+"%"));
                }
                if (condition.getStartDate()!=null && !"".equals(condition.getStartDate())) {
                    listPredicates.add(cb.greaterThanOrEqualTo(root.get("date").as(Date.class),
                            condition.getStartDate()));
                }
                if (condition.getEndDate()!=null && !"".equals(condition.getEndDate())) {
                    listPredicates.add(cb.lessThanOrEqualTo(root.get("date").as(Date.class),
                            condition.getEndDate()));
                }
//                listPredicates.add(cb.equal(root.get("idel").as(Integer.class),0 ));
                Predicate[] arrayPredicates = new Predicate[listPredicates.size()];
                Predicate p = cb.and(listPredicates.toArray(arrayPredicates));
                return p;
            }
        };
        return spec;
    }
}
