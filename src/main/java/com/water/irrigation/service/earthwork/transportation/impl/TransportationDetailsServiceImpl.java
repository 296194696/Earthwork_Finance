package com.water.irrigation.service.earthwork.transportation.impl;


import com.water.irrigation.dao.earthwork.ExpenditureDao;
import com.water.irrigation.dao.earthwork.TransportationDetailsDao;
import com.water.irrigation.entity.dto.erathwork.TransportationDetailsDto;
import com.water.irrigation.entity.erathwork.Expenditure;
import com.water.irrigation.entity.erathwork.TransportationDetails;
import com.water.irrigation.service.earthwork.transportation.TransportationDetailsService;
import com.water.irrigation.utils.Maths;
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
public class TransportationDetailsServiceImpl implements TransportationDetailsService {

    @Autowired
    private TransportationDetailsDao transportationDetailsDao;
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
    public Page<TransportationDetails> findAll(TransportationDetailsDto conditions, Pageable pageable) {
        return transportationDetailsDao.findAll(getSpecification(conditions), pageable);
    }

    /**
     * 根据编号获取对应的资产数据
     * @param indocno 主键
     * @return 指定的资产信息
     */
    @Override
    public TransportationDetails findOne(Long indocno) {
        return transportationDetailsDao.findById(indocno).get();
    }

    /**
     * 计算金额并添加到支出款项
     * @param transportationDetails
     */
    private void validBeforeAddOrUpdate(TransportationDetails transportationDetails){
        Double unitprice = transportationDetails.getUnitprice();
        if(unitprice==null){
            Double kilometres = transportationDetails.getKilometres();
            Double scost = transportationDetails.getScost();
            unitprice=Maths.add(kilometres*10,scost);
            transportationDetails.setUnitprice(unitprice);
        }
        Double itrip = transportationDetails.getItrip();
        transportationDetails.setStotal(Maths.multi(unitprice,itrip.doubleValue()));
    }

    /**
     * 组装支出信息
     */
    private void buildExpenditureAndSave(TransportationDetails transportationDetails){
        Expenditure expenditure=new Expenditure();
        expenditure.setDate(transportationDetails.getDate());
        expenditure.setAmount(transportationDetails.getStotal());
        expenditure.setSaccount(transportationDetails.getSname());
        expenditure.setSrevenue("卸土费");
        String msg=transportationDetails.getSname()+"从"+transportationDetails.getPlaceStart()+"到"+transportationDetails.getPlaceEnd()+
                "的"+transportationDetails.getItrip()+"X"+transportationDetails.getUnitprice()+"卸土费";
        expenditure.setSremarks(msg);
        expenditure.setItranid(transportationDetails.getIndocno());
        expenditureDao.save(expenditure);
    }

    /**
     * 组装支出信息
     */
    private void buildExpenditureAndSaveForUpdate(TransportationDetails transportationDetails){
        Expenditure expenditure=expenditureDao.findByItranid(transportationDetails.getIndocno());
        expenditure.setDate(transportationDetails.getDate());
        expenditure.setAmount(transportationDetails.getStotal());
        expenditure.setSaccount(transportationDetails.getSname());
        expenditure.setSrevenue("卸土费");
        String msg=transportationDetails.getSname()+"从"+transportationDetails.getPlaceStart()+"到"+transportationDetails.getPlaceEnd()+
                "的"+transportationDetails.getItrip()+"X"+transportationDetails.getUnitprice()+"卸土费";
        expenditure.setSremarks(msg);
        expenditureDao.save(expenditure);
    }
    /**
     * 添加资产
     * @param transportationDetails 要添加的资产信息
     * @return 添加的资产信息
     */
    @Override
    public TransportationDetails add(TransportationDetails transportationDetails) {
        validBeforeAddOrUpdate(transportationDetails);
        TransportationDetails newTransportationDetails = transportationDetailsDao.save(transportationDetails);
        buildExpenditureAndSave(newTransportationDetails);
        return  newTransportationDetails;
    }

    /**
     * 修改资产
     * @param transportationDetails 资产信息
     * @return  更新后资产信息
     */
    @Override
    public TransportationDetails update(TransportationDetails transportationDetails) {
        TransportationDetails orgObj =
                this.transportationDetailsDao.findById(transportationDetails.getIndocno()).get();
        if(orgObj!=null) {
            System.out.println("无效的修改对象");
        }
        //UpdateHelperUtils.copyNullProperties(orgObj, TransportationDetails);
        validBeforeAddOrUpdate(transportationDetails);
        buildExpenditureAndSaveForUpdate(transportationDetails);
        return transportationDetailsDao.save(transportationDetails);
    }

    /**
     * 删除生成的支出款
     */
    private void beforeDeleteTran(TransportationDetails transportationDetails){
        Expenditure expenditure = expenditureDao.findByItranid(transportationDetails.getIndocno());
        if(expenditure!=null){
            expenditureDao.delete(expenditure);
        }
    }

    /**
     * 删除资产信息
     * @param sysCompanys 需要删除的资产信息
     */
    @Override
    public void delete(List<TransportationDetails> sysCompanys) {
        for(TransportationDetails transportationDetails : sysCompanys ) {
            beforeDeleteTran(transportationDetails);
            transportationDetailsDao.deleteById(transportationDetails.getIndocno());
        }
    }

    /**
     * 查询条件构建
     * @param condition
     * @return 查询条件
     */
    private Specification<TransportationDetails> getSpecification(TransportationDetailsDto condition){
        // 查询条件构造
        Specification<TransportationDetails> spec = new Specification<TransportationDetails>(){

            private static final long serialVersionUID = 3652606507255419478L;

            @Override
            public Predicate toPredicate(Root<TransportationDetails> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> listPredicates = new ArrayList<Predicate>();
                if (condition.getSname()!=null && !"".equals(condition.getSname())) {
                    listPredicates.add(cb.like(root.get("sname").as(String.class),
                            "%"+condition.getSname()+"%"));
                }
                if (condition.getScard()!=null && !"".equals(condition.getScard())) {
                    listPredicates.add(cb.like(root.get("scard").as(String.class),
                            "%"+condition.getScard()+"%"));
                }
                if (condition.getPlaceStart()!=null && !"".equals(condition.getPlaceStart())) {
                    listPredicates.add(cb.like(root.get("placeStart").as(String.class),
                            "%"+condition.getPlaceStart()+"%"));
                }
                if (condition.getPlaceEnd()!=null && !"".equals(condition.getPlaceEnd())) {
                    listPredicates.add(cb.like(root.get("placeEnd").as(String.class),
                            "%"+condition.getPlaceEnd()+"%"));
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
