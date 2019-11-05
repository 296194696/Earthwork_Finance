package com.water.irrigation.service.earthwork.expenditure.impl;


import com.water.irrigation.dao.earthwork.ExpenditureDao;
import com.water.irrigation.entity.dto.erathwork.ExpenditureDto;
import com.water.irrigation.entity.erathwork.Expenditure;
import com.water.irrigation.service.earthwork.expenditure.ExpenditureService;
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
public class ExpenditureServiceImpl implements ExpenditureService {

    @Autowired
    private ExpenditureDao expenditureDao;

    /**
     * 根据条件获取对应的资产信息数据
     *
     * @param conditions 资产信息过滤条件
     * @param pageable   分页
     * @return 指定的资产信息列表
     */
    @Override
    public Page<Expenditure> findAll(ExpenditureDto conditions, Pageable pageable) {
        return expenditureDao.findAll(getSpecification(conditions), pageable);
    }

    /**
     * 根据编号获取对应的资产数据
     * @param indocno 主键
     * @return 指定的资产信息
     */
    @Override
    public Expenditure findOne(Long indocno) {
        return expenditureDao.findById(indocno).get();
    }

    /**
     * 添加资产
     * @param expenditure 要添加的资产信息
     * @return 添加的资产信息
     */
    @Override
    public Expenditure add(Expenditure expenditure) {
        return expenditureDao.save(expenditure);
    }

    /**
     * 修改资产
     * @param expenditure 资产信息
     * @return  更新后资产信息
     */
    @Override
    public Expenditure update(Expenditure expenditure) {
        Expenditure orgObj =
                this.expenditureDao.findById(expenditure.getIndocno()).get();
        if(orgObj!=null) {
            System.out.println("无效的修改对象");
        }
        //UpdateHelperUtils.copyNullProperties(orgObj, Customer);
        return expenditureDao.save(expenditure);
    }

    /**
     * 删除资产信息
     * @param sysCompanys 需要删除的资产信息
     */
    @Override
    public void delete(List<Expenditure> sysCompanys) {
        for(Expenditure expenditure : sysCompanys ) {
            expenditureDao.deleteById(expenditure.getIndocno());
        }
    }

    /**
     * 查询条件构建
     * @param condition
     * @return 查询条件
     */
    private Specification<Expenditure> getSpecification(ExpenditureDto condition){
        // 查询条件构造
        Specification<Expenditure> spec = new Specification<Expenditure>(){

            private static final long serialVersionUID = 3652606507255419478L;

            @Override
            public Predicate toPredicate(Root<Expenditure> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
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
