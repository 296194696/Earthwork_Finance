package com.water.irrigation.service.earthwork.advance;

import com.water.irrigation.entity.dto.erathwork.AdvanceDto;
import com.water.irrigation.entity.erathwork.Advance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 预支出信息服务层接口
 * @author lichunlei
 *
 */
public interface AdvanceService {

    /**
     * 根据条件获取对应的预支出信息数据
     *
     * @param conditions 预支出信息过滤条件
     * @param pageable   分页
     * @return 指定的预支出信息列表
     */
    Page<Advance> findAll(AdvanceDto conditions, Pageable pageable);

    Advance findOne(Long indocno);

    Advance add(Advance advance);

    Advance update(Advance advance);

    void delete(List<Advance> sysCompanys);


}
