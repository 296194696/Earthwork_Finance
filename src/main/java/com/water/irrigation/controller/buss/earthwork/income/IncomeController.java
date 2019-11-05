package com.water.irrigation.controller.buss.earthwork.income;


import com.water.irrigation.entity.base.ResultEntity;
import com.water.irrigation.entity.dto.erathwork.IncomeDto;
import com.water.irrigation.entity.erathwork.Income;
import com.water.irrigation.service.earthwork.income.IncomeService;
import com.water.irrigation.utils.dozer.DozerHelperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * 收入信息控制器
 * @author lichunlei
 *
 */
@RequestMapping("income/info")
@RestController
@Slf4j
public class IncomeController {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private DozerHelperUtils dozerHelperUtils;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期 注意这里的转化要和传进来的字符串的格式一直 如2015-9-9 就应该为yyyy-MM-dd
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }

    /**
     * 获取 收入数据列表
     * @param customerDto  收入数据传输对象
     * @return 请求返回实体信息
     */
    @RequestMapping("readall")
    public ResultEntity readAll(IncomeDto customerDto){

        ResultEntity resultEntity = new ResultEntity();
        try {
            List<Sort.Order> orders=new ArrayList<Sort.Order>();
            Sort.Order order = new Sort.Order(Sort.Direction.ASC,"date");
            Sort.Order order1 = new Sort.Order(Sort.Direction.ASC,"indocno");
            orders.add(order);
            orders.add(order1);
            Sort sort = new Sort(orders);
            Integer pageno = customerDto.getPage();
            Integer pagesize = customerDto.getLimit();
            pageno = pageno >= 1 ? pageno-1 : 0;

            Pageable pageable = PageRequest.of(pageno,pagesize,sort);
            Page<Income> classifies =
                    this.incomeService.findAll(customerDto,pageable);
            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            resultEntity.setContent(classifies.getContent());
            resultEntity.setCount(classifies.getTotalElements());
        }catch(Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("获取 收入数据出错:"+e.getMessage());
            log.error("获取收入数据出错", e);
        }
        return resultEntity;
    }

    /**
     * 添加 收入信息
     * @param customerDto 收入表数据传输对象
     * @return 请求返回实体信息
     */
    @RequestMapping("add")
    public ResultEntity add(IncomeDto customerDto) {
        ResultEntity resultEntity = new ResultEntity();
        try {

            Income Income =
                    dozerHelperUtils.convert(customerDto, Income.class);

            Income result = this.incomeService.add(Income);

            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            resultEntity.setContent(result);
        }catch (Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("添加收入信息出错:"+e.getMessage());
            log.error("添加 收入信息出错", e);
        }
        return resultEntity;
    }

    /**
     * 修改 收入表信息
     * @param customerDto 收入表数据传输对象
     * @return 请求返回实体信息
     */
    @RequestMapping("update")
    public ResultEntity update(IncomeDto customerDto) {
        ResultEntity resultEntity = new ResultEntity();
        try {

            Income Income =
                    dozerHelperUtils.convert(customerDto, Income.class);
            Income result = this.incomeService.
                    update(Income);

            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            resultEntity.setContent(result);
        }catch (Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("修改 收入信息出错:"+e.getMessage());
            log.error("修改 收入信息出错", e);
        }
        return resultEntity;
    }

    /**
     * 删除 收入表信息
     * @param customerDto 收入表数据传输对象
     * @return 返回删除是否成功得对象
     */
    @RequestMapping("delete")
    public ResultEntity delete(IncomeDto customerDto) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            List<Income> ghjfComtypr =
                    customerDto.getDelLists();

            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            this.incomeService.delete(ghjfComtypr);
        }catch (Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("删除 收入信息出错:"+e.getMessage());
            log.error("删除 收入信息出错", e);
        }
        return resultEntity;
    }

}
