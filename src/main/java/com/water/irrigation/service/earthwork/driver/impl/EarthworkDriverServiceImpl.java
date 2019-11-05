package com.water.irrigation.service.earthwork.driver.impl;

import com.water.irrigation.dao.earthwork.EarthworkDriverDao;
import com.water.irrigation.dao.earthwork.ExpenditureDao;
import com.water.irrigation.dao.earthwork.IncomeDao;
import com.water.irrigation.dao.earthwork.TransportationDetailsDao;
import com.water.irrigation.entity.dto.erathwork.EarthworkDriverDto;
import com.water.irrigation.entity.erathwork.EarthworkDriver;
import com.water.irrigation.service.earthwork.driver.EarthworkDriverService;
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
 * 司机信息服务层
 * @author lichunlei
 *
 */
@Service
public class EarthworkDriverServiceImpl implements EarthworkDriverService {

    @Autowired
    private EarthworkDriverDao earthworkDriverDao;
    @Autowired
    private TransportationDetailsDao transportationDetailsDao;
    @Autowired
    private ExpenditureDao expenditureDao;
    @Autowired
    private IncomeDao incomeDao;

    /**
     * 根据条件获取对应的司机信息数据
     *
     * @param conditions 司机信息过滤条件
     * @param pageable   分页
     * @return 指定的司机信息列表
     */
    @Override
    public Page<EarthworkDriver> findAll(EarthworkDriverDto conditions, Pageable pageable) {
        return earthworkDriverDao.findAll(getSpecification(conditions), pageable);
    }

    /**
     * 根据编号获取对应的司机数据
     * @param indocno 主键
     * @return 指定的司机信息
     */
    @Override
    public EarthworkDriver findOne(Long indocno) {
        return earthworkDriverDao.findById(indocno).get();
    }

    /**
     * 根据车牌号获取
     * @param scard
     * @return 指定的司机信息
     */
    @Override
    public EarthworkDriver findByScard(String scard) {
        return earthworkDriverDao.findByScard(scard);
    }

    /**
     * 根据姓名获取
     * @param sname
     * @return 指定的司机信息
     */
    @Override
    public EarthworkDriver findBySname(String sname) {
        return earthworkDriverDao.findBySname(sname);
    }

    /**
     * 判断添加司机和科目
     * @param earthworkDriver
     */
    private void checkBeforeAddAndUpdate(EarthworkDriver earthworkDriver){
        if(earthworkDriver.getScard()==null){
            earthworkDriver.setItype(1);
        }else {
            earthworkDriver.setItype(0);
        }
    }

    /**
     * 修改后姓名后同步运输单和支出单
     * @param oldName
     * @param newName
     */
    private void snycExpenditureAndTransportaionBeforeModify(String oldName,String newName){
        transportationDetailsDao.syncTranBeforeModifyDriver(oldName,newName);
        expenditureDao.syncExpeBeforeModifyDriver(oldName,newName);
        incomeDao.syncIncomeBeforeModifyDriver(oldName,newName);
    }

    /**
     * 添加司机
     * @param earthworkDriver 要添加的司机信息
     * @return 添加的司机信息
     */
    @Override
    public EarthworkDriver add(EarthworkDriver earthworkDriver) {
        checkBeforeAddAndUpdate(earthworkDriver);
        return earthworkDriverDao.save(earthworkDriver);
    }

    /**
     * 修改司机
     * @param earthworkDriver 司机信息
     * @return  更新后司机信息
     */
    @Override
    public EarthworkDriver update(EarthworkDriver earthworkDriver) {
        checkBeforeAddAndUpdate(earthworkDriver);
        EarthworkDriver oldValue =
                this.earthworkDriverDao.findById(earthworkDriver.getIndocno()).get();
        String oldSname = oldValue.getSname();
        String newSname = earthworkDriver.getSname();
        //UpdateHelperUtils.copyNullProperties(orgObj, EarthworkDriver);
        EarthworkDriver newDriver = earthworkDriverDao.save(earthworkDriver);
        snycExpenditureAndTransportaionBeforeModify(oldSname,newSname);
        return newDriver;
    }

    /**
     * 删除司机信息
     * @param sysCompanys 需要删除的司机信息
     */
    @Override
    public void delete(List<EarthworkDriver> sysCompanys) {
        for(EarthworkDriver earthworkDriver : sysCompanys ) {
            earthworkDriverDao.deleteById(earthworkDriver.getIndocno());
        }
    }

    /**
     * 查询条件构建
     * @param condition
     * @return 查询条件
     */
    private Specification<EarthworkDriver> getSpecification(EarthworkDriverDto condition){
        // 查询条件构造
        Specification<EarthworkDriver> spec = new Specification<EarthworkDriver>(){

            private static final long serialVersionUID = 3652606507255419478L;

            @Override
            public Predicate toPredicate(Root<EarthworkDriver> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> listPredicates = new ArrayList<Predicate>();
                if (condition.getScard()!=null && !"".equals(condition.getScard())) {
                    listPredicates.add(cb.like(root.get("scard").as(String.class),
                            "%"+condition.getScard()+"%"));
                }
                if (condition.getSname()!=null && !"".equals(condition.getSname())) {
                    listPredicates.add(cb.like(root.get("sname").as(String.class),
                            "%"+condition.getSname()+"%"));
                }
                if (condition.getPhone()!=null && !"".equals(condition.getPhone())) {
                    listPredicates.add(cb.like(root.get("phone").as(String.class),
                            "%"+condition.getPhone()+"%"));
                }
                if (condition.getItype()!=null && !"".equals(condition.getItype())) {
                    listPredicates.add(cb.equal(root.get("itype").as(Integer.class),
                            condition.getItype()));
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
