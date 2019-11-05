package com.water.irrigation.controller.buss.earthwork.revenue;


import com.water.irrigation.entity.base.ResultEntity;
import com.water.irrigation.entity.dto.erathwork.RevenueAccountDto;
import com.water.irrigation.entity.erathwork.RevenueAccount;
import com.water.irrigation.service.earthwork.revenue.RevenueAccountService;
import com.water.irrigation.utils.dozer.DozerHelperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 对公信息控制器
 * @author lichunlei
 *
 */
@RequestMapping("revenue/info")
@RestController
@Slf4j
public class RevenueAccountController {

    @Autowired
    private RevenueAccountService revenueAccountService;

    @Autowired
    private DozerHelperUtils dozerHelperUtils;

    /**
     * 获取 对公数据列表
     * @param revenueAccountDto  对公数据传输对象
     * @return 请求返回实体信息
     */
    @RequestMapping("readall")
    public ResultEntity readAll(RevenueAccountDto revenueAccountDto){

        ResultEntity resultEntity = new ResultEntity();
        try {
            Sort sort = new Sort(Sort.Direction.ASC,"indocno");
            Integer pageno = revenueAccountDto.getPage();
            Integer pagesize = revenueAccountDto.getLimit();
            pageno = pageno >= 1 ? pageno-1 : 0;

            Pageable pageable = PageRequest.of(pageno,pagesize,sort);
            Page<RevenueAccount> classifies =
                    this.revenueAccountService.findAll(revenueAccountDto,pageable);
            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            resultEntity.setContent(classifies.getContent());
            resultEntity.setCount(classifies.getTotalElements());
        }catch(Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("获取 对公数据出错:"+e.getMessage());
            log.error("获取对公数据出错", e);
        }
        return resultEntity;
    }
    /**
     * 获取 对公数据列表
     * @param revenueAccountDto  对公数据传输对象
     * @return 请求返回实体信息
     */
    @RequestMapping("readalladvance")
    public ResultEntity readalladvance(RevenueAccountDto revenueAccountDto){

        ResultEntity resultEntity = new ResultEntity();
        try {
            Sort sort = new Sort(Sort.Direction.ASC,"indocno");
            Integer pageno = revenueAccountDto.getPage();
            Integer pagesize = revenueAccountDto.getLimit();
            pageno = pageno >= 1 ? pageno-1 : 0;
            revenueAccountDto.setSname("预");
            Pageable pageable = PageRequest.of(pageno,pagesize,sort);
            Page<RevenueAccount> classifies =
                    this.revenueAccountService.findAll(revenueAccountDto,pageable);
            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            resultEntity.setContent(classifies.getContent());
            resultEntity.setCount(classifies.getTotalElements());
        }catch(Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("获取 对公数据出错:"+e.getMessage());
            log.error("获取对公数据出错", e);
        }
        return resultEntity;
    }

    /**
     * 柱状图月份金额
     * @return
     */
    @RequestMapping("monthAmount")
    public List<String> findYearData(String year){
        if(year==null){
            Calendar c = Calendar.getInstance();
            int y = c.get(Calendar.YEAR);
            year=String.valueOf(y);
        }
        List<RevenueAccount> revenueAccounts = revenueAccountService.findYearData(year);
        List<String> amountList=new ArrayList<String>(12);
        for(RevenueAccount revenueAccount:revenueAccounts){
            amountList.add(revenueAccount.getSregid());
        }
        return amountList;
    }

    /**
     * 添加 对公信息
     * @param revenueAccountDto 对公表数据传输对象
     * @return 请求返回实体信息
     */
    @RequestMapping("add")
    public ResultEntity add(RevenueAccountDto revenueAccountDto) {
        ResultEntity resultEntity = new ResultEntity();
        try {

            RevenueAccount revenueAccount =
                    dozerHelperUtils.convert(revenueAccountDto, RevenueAccount.class);

            RevenueAccount result = this.revenueAccountService.add(revenueAccount);

            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            resultEntity.setContent(result);
        }catch (Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("添加对公信息出错:"+e.getMessage());
            log.error("添加 对公信息出错", e);
        }
        return resultEntity;
    }

    /**
     * 修改 对公表信息
     * @param revenueAccountDto 对公表数据传输对象
     * @return 请求返回实体信息
     */
    @RequestMapping("update")
    public ResultEntity update(RevenueAccountDto revenueAccountDto) {
        ResultEntity resultEntity = new ResultEntity();
        try {

            RevenueAccount revenueAccount =
                    dozerHelperUtils.convert(revenueAccountDto, RevenueAccount.class);
            RevenueAccount result = this.revenueAccountService.
                    update(revenueAccount);

            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            resultEntity.setContent(result);
        }catch (Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("修改 对公信息出错:"+e.getMessage());
            log.error("修改 对公信息出错", e);
        }
        return resultEntity;
    }

    /**
     * 删除 对公表信息
     * @param revenueAccountDto 对公表数据传输对象
     * @return 返回删除是否成功得对象
     */
    @RequestMapping("delete")
    public ResultEntity delete(RevenueAccountDto revenueAccountDto) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            List<RevenueAccount> ghjfComtypr =
                    revenueAccountDto.getDelLists();

            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            this.revenueAccountService.delete(ghjfComtypr);
        }catch (Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("删除 对公信息出错:"+e.getMessage());
            log.error("删除 对公信息出错", e);
        }
        return resultEntity;
    }

}
