package com.water.irrigation.dao.earthwork;

import com.water.irrigation.entity.erathwork.RevenueAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 收支科目Dao
 * @author lichunlei
 *
 */
public interface RevenueAccountDao extends JpaRepository<RevenueAccount,Long>, JpaSpecificationExecutor<RevenueAccount> {

    @Query(value = "select indocno,month as sname,ifnull(amount,0) as sregid,dregt from ( " +
            "select CONCAT(?1,'01') as mon, '一月'as month, '1'as indocno from dual union all  " +
            "select CONCAT(?1,'02') as mon, '二月'as month, '2'as indocno from dual union all  " +
            "select CONCAT(?1,'03') as mon, '三月'as month, '3'as indocno from dual union all  " +
            "select CONCAT(?1,'04') as mon, '四月'as month, '4'as indocno from dual union all  " +
            "select CONCAT(?1,'05') as mon, '五月'as month, '5'as indocno from dual union all  " +
            "select CONCAT(?1,'06') as mon, '六月'as month, '6'as indocno from dual union all  " +
            "select CONCAT(?1,'07') as mon, '七月'as month, '7'as indocno from dual union all  " +
            "select CONCAT(?1,'08') as mon, '八月'as month, '8'as indocno from dual union all  " +
            "select CONCAT(?1,'09') as mon, '九月'as month, '9'as indocno from dual union all  " +
            "select CONCAT(?1,'10') as mon, '十月'as month, '10'as indocno from dual union all  " +
            "select CONCAT(?1,'11') as mon, '十一月'as month, '11'as indocno from dual union all  " +
            "select CONCAT(?1,'12') as mon, '十二月'as month, '12'as indocno from dual ) a \n" +
            "left join   " +
            "(SELECT DATE_FORMAT(date,'%Y%m') smon,sum(stotal) as amount,s.dregt from earthwork_transportation_details s  " +
            " where DATE_FORMAT(date,'%Y')=?1 GROUP BY  " +
            "DATE_FORMAT(date,'%Y%m')) b  " +
            "on a.mon = b.smon",nativeQuery = true)
    public List<RevenueAccount> findYearData(String year);
}
