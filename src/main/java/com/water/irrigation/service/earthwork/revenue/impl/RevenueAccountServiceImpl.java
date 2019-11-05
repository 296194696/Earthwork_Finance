package com.water.irrigation.service.earthwork.revenue.impl;


import com.water.irrigation.dao.earthwork.RevenueAccountDao;
import com.water.irrigation.entity.dto.erathwork.RevenueAccountDto;
import com.water.irrigation.entity.erathwork.RevenueAccount;
import com.water.irrigation.entity.erathwork.TransportationDetails;
import com.water.irrigation.service.earthwork.revenue.RevenueAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


/**
 * 资产信息服务层
 * @author lichunlei
 *
 */
@Service
public class RevenueAccountServiceImpl implements RevenueAccountService {

    @Autowired
    private RevenueAccountDao revenueAccountDao;

    /**
     * 根据条件获取对应的资产信息数据
     *
     * @param conditions 资产信息过滤条件
     * @param pageable   分页
     * @return 指定的资产信息列表
     */
    @Override
    public Page<RevenueAccount> findAll(RevenueAccountDto conditions, Pageable pageable) {
        return revenueAccountDao.findAll(getSpecification(conditions), pageable);
    }

    /**
     * 获取运输每月金额
     * 供图表示使用
     * @return
     */
    @Override
    public List<RevenueAccount> findYearData(String year){
        return revenueAccountDao.findYearData(year);
    }

    /**
     * 根据编号获取对应的资产数据
     * @param indocno 主键
     * @return 指定的资产信息
     */
    @Override
    public RevenueAccount findOne(Long indocno) {
        return revenueAccountDao.findById(indocno).get();
    }

    /**
     * 添加资产
     * @param revenueAccount 要添加的资产信息
     * @return 添加的资产信息
     */
    @Override
    public RevenueAccount add(RevenueAccount revenueAccount) {
        return revenueAccountDao.save(revenueAccount);
    }

    /**
     * 修改资产
     * @param revenueAccount 资产信息
     * @return  更新后资产信息
     */
    @Override
    public RevenueAccount update(RevenueAccount revenueAccount) {
        RevenueAccount orgObj =
                this.revenueAccountDao.findById(revenueAccount.getIndocno()).get();
        if(orgObj!=null) {
            System.out.println("无效的修改对象");
        }
        //UpdateHelperUtils.copyNullProperties(orgObj, RevenueAccount);
        return revenueAccountDao.save(revenueAccount);
    }

    /**
     * 删除资产信息
     * @param sysCompanys 需要删除的资产信息
     */
    @Override
    public void delete(List<RevenueAccount> sysCompanys) {
        for(RevenueAccount revenueAccount : sysCompanys ) {
            revenueAccountDao.deleteById(revenueAccount.getIndocno());
        }
    }

    /**
     * 查询条件构建
     * @param condition
     * @return 查询条件
     */
    private Specification<RevenueAccount> getSpecification(RevenueAccountDto condition){
        // 查询条件构造
        Specification<RevenueAccount> spec = new Specification<RevenueAccount>(){

            private static final long serialVersionUID = 3652606507255419478L;

            @Override
            public Predicate toPredicate(Root<RevenueAccount> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> listPredicates = new ArrayList<Predicate>();
                if (condition.getSname()!=null && !"".equals(condition.getSname())) {
                    listPredicates.add(cb.like(root.get("sname").as(String.class),
                            "%"+condition.getSname()+"%"));
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
