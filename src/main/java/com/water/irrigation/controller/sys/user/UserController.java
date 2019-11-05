package com.water.irrigation.controller.sys.user;

import com.water.irrigation.entity.base.ResultEntity;
import com.water.irrigation.entity.dto.sys.user.SysUserDto;
import com.water.irrigation.entity.sys.user.SysUser;
import com.water.irrigation.service.sys.user.SysUserService;
import com.water.irrigation.utils.BaseMsg;
import com.water.irrigation.utils.ResponseBean;
import com.water.irrigation.utils.dozer.DozerHelperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

@Controller
@Slf4j
public class UserController {


    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private DozerHelperUtils dozerHelperUtils;

    /**
     * 检查用户名是否存在
     * @param username
     * @return
     *
     */
    @RequestMapping("/checkNameIsExistOrNot")
    @ResponseBody
    public ResponseBean checkNameIsExistOrNot(String username){
        boolean flag=sysUserService.checkNameIsExistOrNot(username);
        if(flag){
            return ResponseBean.ok(BaseMsg.success(100001));
        }else{
            return ResponseBean.error(BaseMsg.success(100001));
        }
    }

    /**
     * 注册接口
     * @param sysUser
     * @return
     */
    @RequestMapping("/register_form")
    @ResponseBody
    public ResponseBean registerForm(SysUser sysUser){

        try{
            Integer iroleid= Integer.valueOf(sysUser.getIroleid());
            if(iroleid==2){
                sysUser.setRole("mito");
                sysUser.setRolename("普通用水户");
            }
            if(iroleid==3){
                sysUser.setRole("dept");
                sysUser.setRolename("用水单位");
            }
            sysUserService.saveUser(sysUser);
            return ResponseBean.ok(100001);
        }catch (Exception e){
            return ResponseBean.error(100001);
        }
    }

    /**
     * 获取 用户数据列表
     * @param sysUserDto  用户数据传输对象
     * @return 请求返回实体信息
     */
    @RequestMapping("user/info/readall")
    @ResponseBody
    public ResultEntity readAll(SysUserDto sysUserDto){

        ResultEntity resultEntity = new ResultEntity();
        try {
            Sort sort = new Sort(Sort.Direction.ASC,"indocno");
            Integer pageno = sysUserDto.getPage();
            Integer pagesize = sysUserDto.getLimit();
            pageno = pageno >= 1 ? pageno-1 : 0;

            Pageable pageable = PageRequest.of(pageno,pagesize,sort);
            Page<SysUser> classifies =
                    this.sysUserService.findAll(sysUserDto,pageable);
            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            resultEntity.setContent(classifies.getContent());
            resultEntity.setCount(classifies.getTotalElements());
        }catch(Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("获取 用户数据出错:"+e.getMessage());
            log.error("获取用户数据出错", e);
        }
        return resultEntity;
    }

    /**
     * 修改 用户表信息
     * @param sysUserDto 用户表数据传输对象
     * @return 请求返回实体信息
     */
    @RequestMapping("user/info/update")
    @ResponseBody
    public ResultEntity update(SysUserDto sysUserDto) {
        ResultEntity resultEntity = new ResultEntity();
        try {

            SysUser sysUser =
                    dozerHelperUtils.convert(sysUserDto, SysUser.class);
            Integer iroleid= Integer.valueOf(sysUser.getIroleid());
            if(iroleid==1){
                sysUser.setRole("admin");
                sysUser.setRolename("系统管理员");
            }
            if(iroleid==2){
                sysUser.setRole("mito");
                sysUser.setRolename("操作人员");
            }
            SysUser result = this.sysUserService.
                    update(sysUser);

            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            resultEntity.setContent(result);
        }catch (Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("修改 用户信息出错:"+e.getMessage());
            log.error("修改 用户信息出错", e);
        }
        return resultEntity;
    }

    /**
     * 添加 用户表信息
     * @param sysUserDto 用户表数据传输对象
     * @return 请求返回实体信息
     */
    @RequestMapping("user/info/add")
    @ResponseBody
    public ResultEntity add(SysUserDto sysUserDto) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            SysUser sysUser =
                    dozerHelperUtils.convert(sysUserDto, SysUser.class);
            Integer iroleid= Integer.valueOf(sysUser.getIroleid());
            if(iroleid==1){
                sysUser.setRole("admin");
                sysUser.setRolename("系统管理员");
            }
            if(iroleid==2){
                sysUser.setRole("mito");
                sysUser.setRolename("操作人员");
            }
            SysUser result = this.sysUserService.
                    saveUser(sysUser);
            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            resultEntity.setContent(result);
        }catch (Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("修改 用户信息出错:"+e.getMessage());
            log.error("修改 用户信息出错", e);
        }
        return resultEntity;
    }

    /**
     * 删除 用户表信息
     * @param sysUserDto 用户表数据传输对象
     * @return 返回删除是否成功得对象
     */
    @RequestMapping("user/info/delete")
    @ResponseBody
    public ResultEntity delete(SysUserDto sysUserDto) {
        ResultEntity resultEntity = new ResultEntity();
        try {
            List<SysUser> ghjfComtypr =
                    sysUserDto.getDelLists();

            resultEntity.setCode(ResultEntity.StatusCode.SUCCESS.getCode());
            this.sysUserService.delete(ghjfComtypr);
        }catch (Exception e) {
            resultEntity.setCode(ResultEntity.StatusCode.FAILURE.getCode());
            resultEntity.setMsg("删除 用户信息出错:"+e.getMessage());
            log.error("删除 用户信息出错", e);
        }
        return resultEntity;
    }

}
