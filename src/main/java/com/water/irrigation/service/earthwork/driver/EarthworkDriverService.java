package com.water.irrigation.service.earthwork.driver;

import com.water.irrigation.entity.dto.erathwork.EarthworkDriverDto;
import com.water.irrigation.entity.erathwork.EarthworkDriver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 资产信息服务层接口
 * @author lichunlei
 *
 */
public interface EarthworkDriverService {

    /**
     * 根据条件获取对应的资产信息数据
     *
     * @param conditions 资产信息过滤条件
     * @param pageable   分页
     * @return 指定的资产信息列表
     */
    Page<EarthworkDriver> findAll(EarthworkDriverDto conditions, Pageable pageable);

    EarthworkDriver findOne(Long indocno);

    EarthworkDriver add(EarthworkDriver earthworkDriver);

    EarthworkDriver update(EarthworkDriver earthworkDriver);

    void delete(List<EarthworkDriver> sysCompanys);

    public EarthworkDriver findByScard(String scard);

    public EarthworkDriver findBySname(String sname);

}
