package com.water.irrigation.service.earthwork.expenditure;

import com.water.irrigation.entity.dto.erathwork.ExpenditureDto;
import com.water.irrigation.entity.erathwork.Expenditure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 资产信息服务层接口
 * @author lichunlei
 *
 */
public interface ExpenditureService {

    /**
     * 根据条件获取对应的资产信息数据
     *
     * @param conditions 资产信息过滤条件
     * @param pageable   分页
     * @return 指定的资产信息列表
     */
    Page<Expenditure> findAll(ExpenditureDto conditions, Pageable pageable);

    Expenditure findOne(Long indocno);

    Expenditure add(Expenditure Expenditure);

    Expenditure update(Expenditure Expenditure);

    void delete(List<Expenditure> sysCompanys);


}
