package com.water.irrigation.dao.earthwork;

import com.water.irrigation.entity.erathwork.EarthworkDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 司机Dao
 * @author lichunlei
 *
 */
public interface EarthworkDriverDao extends JpaRepository<EarthworkDriver,Long>, JpaSpecificationExecutor<EarthworkDriver> {


    public EarthworkDriver findByScard(String scard);

    public EarthworkDriver findBySname(String sname);
}
