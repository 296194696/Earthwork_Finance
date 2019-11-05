package com.water.irrigation.controller.sys.index;

import com.water.irrigation.entity.dto.sys.menu.SysMenuDto;
import com.water.irrigation.entity.sys.menu.SysMenu;
import com.water.irrigation.entity.sys.user.SysUser;
import com.water.irrigation.service.sys.menu.SysMenuService;
import com.water.irrigation.service.sys.user.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    @RequestMapping("/index")
    public String index(ModelMap map){

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        String username=userDetails.getUsername();
        SysUser sysUser=sysUserService.findUserByUsername(username);
        SysMenuDto sysMenuDto=new SysMenuDto();
        sysMenuDto.setLevel(1);
        if(sysUser.getIroleid()!=1){
            sysMenuDto.setIroleid(1);
            map.addAttribute("console", "/console");
        }else {
            map.addAttribute("console", "/console");
        }
        Sort sort = new Sort(Sort.Direction.ASC,"score");
        Pageable pageable = PageRequest.of(0,20,sort);
        Page<SysMenu> sysMenus=sysMenuService.findAll(sysMenuDto,pageable);
        List<SysMenu> sysMenuList=sysMenus.getContent();
        if(sysUser.getIroleid()!=1){
            for(SysMenu sysMenu:sysMenus){
                List<SysMenu> child=sysMenu.getChildren();
                List<SysMenu> childList=new ArrayList<SysMenu>();
                for(SysMenu obj:child){
                    if(obj.getIroleid()==1){
                        childList.add(obj);
                    }
                }
                sysMenu.setChildren(childList);
            }
        }
        map.addAttribute("username", sysUser.getSname());
        map.addAttribute("url", "/test");
        map.addAttribute("rolename","您的身份是： "+sysUser.getRolename());
        map.addAttribute("sysMenus", sysMenuList);
        return "views/index";
    }

    @RequestMapping("/console")
    public String console(ModelMap map){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        String username=userDetails.getUsername();
        SysUser sysUser=sysUserService.findUserByUsername(username);
        map.addAttribute("userid", sysUser.getIroleid());
        return "views/home/console";
    }

    @RequestMapping("/index1")
    @ResponseBody
    public List<SysMenu> test(){

        List<SysMenu> sysMenus=sysMenuService.findByLevel(1);

        return sysMenus;
    }

}
