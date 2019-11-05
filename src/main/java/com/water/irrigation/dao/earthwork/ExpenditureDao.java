package com.water.irrigation.dao.earthwork;

import com.water.irrigation.entity.erathwork.Expenditure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

/**
 * 支出Dao
 * @author lichunlei
 *
 */
public interface ExpenditureDao extends JpaRepository<Expenditure,Long>, JpaSpecificationExecutor<Expenditure> {

    public Expenditure findByItranid(Long itranid);

    @Modifying(flushAutomatically = true)
    @Transactional
    @Query(value = "UPDATE earthwork_expenditure a INNER JOIN " +
            " (select * from earthwork_expenditure where saccount=?1) b " +
            " ON a.indocno = b.indocno SET a.saccount = ?2",nativeQuery = true)
    public void syncExpeBeforeModifyDriver(String oldName,String newName);

}
