package com.water.irrigation.controller.buss.earthwork.transportation;


import org.springframework.data.domain.Sort.Order;
import com.water.irrigation.entity.base.ResultEntity;
import com.water.irrigation.entity.dto.erathwork.TransportationDetailsDto;
import com.water.irrigation.entity.erathwork.TransportationDetails;
import com.water.irrigation.service.earthwork.transportation.TransportationDetailsService;
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
 * 运输单信息控制器
 * @author lichunlei
 *
 */
@RequestMapping("transportationDetails/info")
@RestController
@Slf4j
public class TransportationDetailsController {

    @Autowired
    private TransportationDetailsService transportationDetailsService;

    @Autowired
    private DozerHelperUtils dozerHelperUtils;

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {
        //转换日期 注意这里的转化要和传进来的字符串的格式一直 如2015-9-9 就应该为yyyy-MM-dd
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }

    /**
     * 获取 运输单数据列表
     * @param transportationDetailsDto  运输单数据传输对象
     * @return 请求返回实体信息
     */
    @RequestMapping("readall")
    public ResultEntity readAll(TransportationDetailsDto transportationDetailsDto){

        ResultEntity resultEntity = new ResultEntity();
        try {

            List<Order> orders=new ArrayList< Order>();
            Order order = new Order(Sort.Direction.ASC,"date");
            Order order1 = new Order(Sort.Direction.ASC,"indocno");
            orders.add(order);
            orders.add(order1);
            Sort sort = new Sort(orders);

            Integer pageno = transportationDetailsDto.getPage();
            Integer pagesize = transportationDetailsDto.getLimit();
            pageno = pageno >= 1 ? pageno-1 : 0;

            Pageable pageable = PageRequest.of(pageno,pagesize,sort);
            Page<TransportationDetails> classifies =
                    this.transportationDetailsService.findAll(transportationDetailsDto,pageable);
            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            resultEntity.setContent(classifies.getContent());
            resultEntity.setCount(classifies.getTotalElements());
        }catch(Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("获取 运输单数据出错:"+e.getMessage());
            log.error("获取运输单数据出错", e);
        }
        return resultEntity;
    }

    /**
     * 添加 运输单信息
     * @param transportationDetailsDto 运输单表数据传输对象
     * @return 请求返回实体信息
     */
    @RequestMapping("add")
    public ResultEntity add(TransportationDetailsDto transportationDetailsDto) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            TransportationDetails transportationDetails =
                    dozerHelperUtils.convert(transportationDetailsDto, TransportationDetails.class);

            TransportationDetails result = this.transportationDetailsService.add(transportationDetails);

            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            resultEntity.setContent(result);
        }catch (Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("添加运输单信息出错:"+e.getMessage());
            log.error("添加 运输单信息出错", e);
        }
        return resultEntity;
    }

    /**
     * 修改 运输单表信息
     * @param transportationDetailsDto 运输单表数据传输对象
     * @return 请求返回实体信息
     */
    @RequestMapping("update")
    public ResultEntity update(TransportationDetailsDto transportationDetailsDto) {
        ResultEntity resultEntity = new ResultEntity();
        try {

            TransportationDetails transportationDetails =
                    dozerHelperUtils.convert(transportationDetailsDto, TransportationDetails.class);
            TransportationDetails result = this.transportationDetailsService.
                    update(transportationDetails);

            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            resultEntity.setContent(result);
        }catch (Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("修改 运输单信息出错:"+e.getMessage());
            log.error("修改 运输单信息出错", e);
        }
        return resultEntity;
    }

    /**
     * 删除 运输单表信息
     * @param transportationDetailsDto 运输单表数据传输对象
     * @return 返回删除是否成功得对象
     */
    @RequestMapping("delete")
    public ResultEntity delete(TransportationDetailsDto transportationDetailsDto) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            List<TransportationDetails> ghjfComtypr =
                    transportationDetailsDto.getDelLists();

            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            this.transportationDetailsService.delete(ghjfComtypr);
        }catch (Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("删除 运输单信息出错:"+e.getMessage());
            log.error("删除 运输单信息出错", e);
        }
        return resultEntity;
    }

}
