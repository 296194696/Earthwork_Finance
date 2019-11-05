package com.water.irrigation.service.earthwork.transportation;

import com.water.irrigation.entity.dto.erathwork.TransportationDetailsDto;
import com.water.irrigation.entity.erathwork.TransportationDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 资产信息服务层接口
 * @author lichunlei
 *
 */
public interface TransportationDetailsService {

    /**
     * 根据条件获取对应的资产信息数据
     *
     * @param conditions 资产信息过滤条件
     * @param pageable   分页
     * @return 指定的资产信息列表
     */
    Page<TransportationDetails> findAll(TransportationDetailsDto conditions, Pageable pageable);

    TransportationDetails findOne(Long indocno);

    TransportationDetails add(TransportationDetails transportationDetails);

    TransportationDetails update(TransportationDetails transportationDetails);

    void delete(List<TransportationDetails> sysCompanys);


}
