package com.water.irrigation.service.earthwork.income;

import com.water.irrigation.entity.dto.erathwork.IncomeDto;
import com.water.irrigation.entity.erathwork.Income;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 资产信息服务层接口
 * @author lichunlei
 *
 */
public interface IncomeService {

    /**
     * 根据条件获取对应的资产信息数据
     *
     * @param conditions 资产信息过滤条件
     * @param pageable   分页
     * @return 指定的资产信息列表
     */
    Page<Income> findAll(IncomeDto conditions, Pageable pageable);

    Income findOne(Long indocno);

    Income add(Income income);

    Income update(Income income);

    void delete(List<Income> sysCompanys);


}
