package com.water.irrigation.entity.dto.erathwork;

import com.water.irrigation.entity.erathwork.Income;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.sql.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class IncomeDto extends Income {

    /**
     * 页数
     */
    private Integer page = 1;

    /**
     * 每页记录数
     */
    private Integer limit = 10;

    /**
     * 开始日期
     */
    private Date startDate;

    /**
     * 结束日期
     */
    private Date endDate;
    /**
     * 需要删除的数据列表
     */
    private List<Income> delLists;
}
