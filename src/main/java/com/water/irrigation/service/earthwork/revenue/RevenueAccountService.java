package com.water.irrigation.service.earthwork.revenue;

import com.water.irrigation.entity.dto.erathwork.RevenueAccountDto;
import com.water.irrigation.entity.erathwork.RevenueAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 资产信息服务层接口
 * @author lichunlei
 *
 */
public interface RevenueAccountService {

    /**
     * 根据条件获取对应的资产信息数据
     *
     * @param conditions 资产信息过滤条件
     * @param pageable   分页
     * @return 指定的资产信息列表
     */
    Page<RevenueAccount> findAll(RevenueAccountDto conditions, Pageable pageable);

    RevenueAccount findOne(Long indocno);

    RevenueAccount add(RevenueAccount revenueAccount);

    RevenueAccount update(RevenueAccount revenueAccount);

    void delete(List<RevenueAccount> sysCompanys);

    List<RevenueAccount> findYearData(String year);

}
