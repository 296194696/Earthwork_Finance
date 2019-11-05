package com.water.irrigation.dao.earthwork;

import com.water.irrigation.entity.erathwork.TransportationDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * 运输单Dao
 * @author lichunlei
 *
 */
public interface TransportationDetailsDao extends JpaRepository<TransportationDetails,Long>, JpaSpecificationExecutor<TransportationDetails> {

    @Modifying(flushAutomatically = true)
    @Transactional
    @Query(value = "UPDATE earthwork_transportation_details a INNER JOIN " +
            " (select * from earthwork_transportation_details where sname=?1) b " +
            " ON a.indocno = b.indocno SET a.sname = ?2",nativeQuery = true)
    public void syncTranBeforeModifyDriver(String oldName, String newName);

}
