package com.water.irrigation.entity.dto.erathwork;

import com.water.irrigation.entity.erathwork.RevenueAccount;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class RevenueAccountDto extends RevenueAccount {

    /**
     * 页数
     */
    private Integer page = 1;

    /**
     * 每页记录数
     */
    private Integer limit = 10;

    /**
     * 需要删除的数据列表
     */
    private List<RevenueAccount> delLists;
}
