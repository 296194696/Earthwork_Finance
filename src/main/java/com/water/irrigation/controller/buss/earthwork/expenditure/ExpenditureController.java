package com.water.irrigation.controller.buss.earthwork.expenditure;


import com.water.irrigation.entity.base.ResultEntity;
import com.water.irrigation.entity.dto.erathwork.ExpenditureDto;
import com.water.irrigation.entity.erathwork.Expenditure;
import com.water.irrigation.service.earthwork.expenditure.ExpenditureService;
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
 * 支出信息控制器
 * @author lichunlei
 *
 */
@RequestMapping("expenditure/info")
@RestController
@Slf4j
public class ExpenditureController {

    @Autowired
    private ExpenditureService expenditureService;

    @Autowired
    private DozerHelperUtils dozerHelperUtils;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期 注意这里的转化要和传进来的字符串的格式一直 如2015-9-9 就应该为yyyy-MM-dd
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }

    /**
     * 获取 支出数据列表
     * @param expenditureDto  支出数据传输对象
     * @return 请求返回实体信息
     */
    @RequestMapping("readall")
    public ResultEntity readAll(ExpenditureDto expenditureDto){

        ResultEntity resultEntity = new ResultEntity();
        try {
            List<Sort.Order> orders=new ArrayList<Sort.Order>();
            Sort.Order order = new Sort.Order(Sort.Direction.ASC,"date");
            Sort.Order order1 = new Sort.Order(Sort.Direction.ASC,"indocno");
            orders.add(order);
            orders.add(order1);
            Sort sort = new Sort(orders);
            Integer pageno = expenditureDto.getPage();
            Integer pagesize = expenditureDto.getLimit();
            pageno = pageno >= 1 ? pageno-1 : 0;

            Pageable pageable = PageRequest.of(pageno,pagesize,sort);
            Page<Expenditure> classifies =
                    this.expenditureService.findAll(expenditureDto,pageable);
            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            resultEntity.setContent(classifies.getContent());
            resultEntity.setCount(classifies.getTotalElements());
        }catch(Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("获取 支出数据出错:"+e.getMessage());
            log.error("获取支出数据出错", e);
        }
        return resultEntity;
    }

    /**
     * 添加 支出信息
     * @param expenditureDto 支出表数据传输对象
     * @return 请求返回实体信息
     */
    @RequestMapping("add")
    public ResultEntity add(ExpenditureDto expenditureDto) {
        ResultEntity resultEntity = new ResultEntity();
        try {

            Expenditure expenditure =
                    dozerHelperUtils.convert(expenditureDto, Expenditure.class);

            Expenditure result = this.expenditureService.add(expenditure);

            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            resultEntity.setContent(result);
        }catch (Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("添加支出信息出错:"+e.getMessage());
            log.error("添加 支出信息出错", e);
        }
        return resultEntity;
    }

    /**
     * 修改 支出表信息
     * @param expenditureDto 支出表数据传输对象
     * @return 请求返回实体信息
     */
    @RequestMapping("update")
    public ResultEntity update(ExpenditureDto expenditureDto) {
        ResultEntity resultEntity = new ResultEntity();
        try {

            Expenditure Expenditure =
                    dozerHelperUtils.convert(expenditureDto, Expenditure.class);
            Expenditure result = this.expenditureService.
                    update(Expenditure);

            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            resultEntity.setContent(result);
        }catch (Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("修改 支出信息出错:"+e.getMessage());
            log.error("修改 支出信息出错", e);
        }
        return resultEntity;
    }

    /**
     * 删除 支出表信息
     * @param expenditureDto 支出表数据传输对象
     * @return 返回删除是否成功得对象
     */
    @RequestMapping("delete")
    public ResultEntity delete(ExpenditureDto expenditureDto) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            List<Expenditure> ghjfComtypr =
                    expenditureDto.getDelLists();

            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            this.expenditureService.delete(ghjfComtypr);
        }catch (Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("删除 支出信息出错:"+e.getMessage());
            log.error("删除 支出信息出错", e);
        }
        return resultEntity;
    }

}
