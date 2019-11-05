package com.water.irrigation.dao.earthwork;

import com.water.irrigation.entity.erathwork.Advance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 收支科目Dao
 * @author lichunlei
 *
 */
public interface AdvanceDao extends JpaRepository<Advance,Long>, JpaSpecificationExecutor<Advance> {


}
