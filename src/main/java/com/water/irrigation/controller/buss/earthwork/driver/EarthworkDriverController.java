package com.water.irrigation.controller.buss.earthwork.driver;


import com.water.irrigation.entity.base.ResultEntity;
import com.water.irrigation.entity.dto.erathwork.EarthworkDriverDto;
import com.water.irrigation.entity.erathwork.EarthworkDriver;
import com.water.irrigation.service.earthwork.driver.EarthworkDriverService;
import com.water.irrigation.utils.dozer.DozerHelperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/**
 * 司机信息控制器
 * @author lichunlei
 *
 */
@RequestMapping("driver/info")
@RestController
@Slf4j
public class EarthworkDriverController {

    @Autowired
    private EarthworkDriverService earthworkDriverService;

    @Autowired
    private DozerHelperUtils dozerHelperUtils;

    /**
     * 获取 司机列表
     * @param earthworkDriverDto  司机传输对象
     * @return 请求返回实体信息
     */
    @RequestMapping("readall")
    public ResultEntity readAll(EarthworkDriverDto earthworkDriverDto){

        ResultEntity resultEntity = new ResultEntity();
        try {
            List<Sort.Order> orders=new ArrayList<Sort.Order>();
            Sort.Order order = new Sort.Order(Sort.Direction.DESC,"itype");
            Sort.Order order1 = new Sort.Order(Sort.Direction.ASC,"indocno");
            orders.add(order);
            orders.add(order1);
            Sort sort = new Sort(orders);
            Integer pageno = earthworkDriverDto.getPage();
            Integer pagesize = earthworkDriverDto.getLimit();
            pageno = pageno >= 1 ? pageno-1 : 0;

            Pageable pageable = PageRequest.of(pageno,pagesize,sort);
            Page<EarthworkDriver> classifies =
                    this.earthworkDriverService.findAll(earthworkDriverDto,pageable);
            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            resultEntity.setContent(classifies.getContent());
            resultEntity.setCount(classifies.getTotalElements());
        }catch(Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("获取 司机出错:"+e.getMessage());
            log.error("获取司机出错", e);
        }
        return resultEntity;
    }

    /**
     * 获取 司机列表
     * @param earthworkDriverDto  司机传输对象
     * @return 请求返回实体信息
     */
    @RequestMapping("readalldriver")
    public ResultEntity readAllDriver(EarthworkDriverDto earthworkDriverDto){

        ResultEntity resultEntity = new ResultEntity();
        try {
            earthworkDriverDto.setItype(0);
            Sort sort = new Sort(Sort.Direction.ASC,"indocno");
            Integer pageno = earthworkDriverDto.getPage();
            Integer pagesize = earthworkDriverDto.getLimit();
            pageno = pageno >= 1 ? pageno-1 : 0;

            Pageable pageable = PageRequest.of(pageno,pagesize,sort);
            Page<EarthworkDriver> classifies =
                    this.earthworkDriverService.findAll(earthworkDriverDto,pageable);
            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            resultEntity.setContent(classifies.getContent());
            resultEntity.setCount(classifies.getTotalElements());
        }catch(Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("获取 司机出错:"+e.getMessage());
            log.error("获取司机出错", e);
        }
        return resultEntity;
    }

    /**
     * 获取 对公账户列表
     * @param earthworkDriverDto  司机传输对象
     * @return 请求返回实体信息
     */
    @RequestMapping("readallaccount")
    public ResultEntity readAllAccount(EarthworkDriverDto earthworkDriverDto){

        ResultEntity resultEntity = new ResultEntity();
        try {
            earthworkDriverDto.setItype(1);
            Sort sort = new Sort(Sort.Direction.ASC,"indocno");
            Integer pageno = earthworkDriverDto.getPage();
            Integer pagesize = earthworkDriverDto.getLimit();
            pageno = pageno >= 1 ? pageno-1 : 0;

            Pageable pageable = PageRequest.of(pageno,pagesize,sort);
            Page<EarthworkDriver> classifies =
                    this.earthworkDriverService.findAll(earthworkDriverDto,pageable);
            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            resultEntity.setContent(classifies.getContent());
            resultEntity.setCount(classifies.getTotalElements());
        }catch(Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("获取 司机出错:"+e.getMessage());
            log.error("获取司机出错", e);
        }
        return resultEntity;
    }

    /**
     * 添加 司机信息
     * @param earthworkDriverDto 司机表数据传输对象
     * @return 请求返回实体信息
     */
    @RequestMapping("add")
    public ResultEntity add(EarthworkDriverDto earthworkDriverDto) {
        ResultEntity resultEntity = new ResultEntity();
        try {

            EarthworkDriver earthworkDriver =
                    dozerHelperUtils.convert(earthworkDriverDto, EarthworkDriver.class);

            EarthworkDriver result = this.earthworkDriverService.add(earthworkDriver);

            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            resultEntity.setContent(result);
        }catch (Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("添加司机信息出错:"+e.getMessage());
            log.error("添加 司机信息出错", e);
        }
        return resultEntity;
    }

    /**
     * 修改 司机表信息
     * @param earthworkDriverDto 司机表数据传输对象
     * @return 请求返回实体信息
     */
    @RequestMapping("update")
    public ResultEntity update(EarthworkDriverDto earthworkDriverDto) {
        ResultEntity resultEntity = new ResultEntity();
        try {

            EarthworkDriver EarthworkDriver =
                    dozerHelperUtils.convert(earthworkDriverDto, EarthworkDriver.class);
            EarthworkDriver result = this.earthworkDriverService.
                    update(EarthworkDriver);

            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            resultEntity.setContent(result);
        }catch (Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("修改 司机信息出错:"+e.getMessage());
            log.error("修改 司机信息出错", e);
        }
        return resultEntity;
    }

    /**
     * 删除 司机表信息
     * @param earthworkDriverDto 司机表数据传输对象
     * @return 返回删除是否成功得对象
     */
    @RequestMapping("delete")
    public ResultEntity delete(EarthworkDriverDto earthworkDriverDto) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            List<EarthworkDriver> ghjfComtypr =
                    earthworkDriverDto.getDelLists();

            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            this.earthworkDriverService.delete(ghjfComtypr);
        }catch (Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("删除 司机信息出错:"+e.getMessage());
            log.error("删除 司机信息出错", e);
        }
        return resultEntity;
    }

    /**
     * 检查车牌号是否已经存在
     * @param scard
     * @return
     */
    @RequestMapping("scard")
    public ResultEntity findByScard(String scard){
        ResultEntity resultEntity = new ResultEntity();
        try {

            EarthworkDriver earthworkDriver = earthworkDriverService.findByScard(scard);
            if (earthworkDriver==null) {
                resultEntity.setCode(1000);
            }else{
                resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            }

        }catch (Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("删除 司机信息出错:"+e.getMessage());
            log.error("删除 司机信息出错", e);
        }
        return resultEntity;
    }

    /**
     * 检查姓名已经存在
     * @param sname
     * @return
     */
    @RequestMapping("sname")
    public ResultEntity findBySname(String sname){
        ResultEntity resultEntity = new ResultEntity();
        try {

            EarthworkDriver earthworkDriver = earthworkDriverService.findBySname(sname);
            if (earthworkDriver==null) {
                resultEntity.setCode(1000);
            }else{
                resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            }

        }catch (Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("删除 司机信息出错:"+e.getMessage());
            log.error("删除 司机信息出错", e);
        }
        return resultEntity;
    }

}
