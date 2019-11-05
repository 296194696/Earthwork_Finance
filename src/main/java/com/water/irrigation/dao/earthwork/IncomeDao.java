package com.water.irrigation.dao.earthwork;

import com.water.irrigation.entity.erathwork.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * 收支科目Dao
 * @author lichunlei
 *
 */
public interface IncomeDao extends JpaRepository<Income,Long>, JpaSpecificationExecutor<Income> {

    @Modifying(flushAutomatically = true)
    @Transactional
    @Query(value = "UPDATE earthwork_income a INNER JOIN " +
            " (select * from earthwork_income where saccount=?1) b " +
            " ON a.indocno = b.indocno SET a.saccount = ?2",nativeQuery = true)
    public void syncIncomeBeforeModifyDriver(String oldName,String newName);

}
