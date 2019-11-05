package com.water.irrigation.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;


@Controller
public class BaseController {

    @RequestMapping("/login_page")
    public String login(){
        return "login_page";
    }

    @RequestMapping("/register_page")
    public String register(){
        return "my/register";
    }

    @RequestMapping("/login_layui")
    public String login1(){
        return "views/user/login";
    }

    @RequestMapping("/user")
    public String list(){
        return "views/user/user/list";
    }

    @RequestMapping("/user/administrators/list")
    public String list1(){
        return "views/user/user/list";
    }

    @RequestMapping("/test")
    public String test(){
        return "test";
    }

    @RequestMapping("/main")
    public String main(){
        return "main";
    }

    @RequestMapping("/table")
    public String table(){
        return "my/displayuserinfo";
    }

    @RequestMapping("/table1")
    public String table1(){
        return "my/change_user_role";
    }

    @RequestMapping("/simple")
    public String simple(){
        return "my/simple";
    }

    @RequestMapping("/userform")
    public String userform(){
        return "views/user/user/userform";
    }

    @RequestMapping("/block")
    public String block(){
        return "my/block";
    }

    @RequestMapping("/blockform")
    public String blockinfo(){
        return "my/blockform";
    }

    @RequestMapping("/cropform")
    public String cropform(){
        return "my/cropform";
    }

    @RequestMapping("/charge")
    public String charge(){
        return "my/charge";
    }

    @RequestMapping("/chargeform")
    public String chargeform(){
        return "my/chargeform";
    }

    @RequestMapping("/staticriver")
    public String staticriver(){
        return "river/static";
    }

    @RequestMapping("/dynamicriver")
    public String dynamicriver(){
        return "river/dynamic";
    }

    @RequestMapping("/staticform")
    public String staticform(){
        return "river/staticform";
    }

    @RequestMapping("/dynamicform")
    public String dynamicform(){
        return "river/dynamicform";
    }

    @RequestMapping("/driver")
    public String driver(){
        return "earthwork/driver";
    }

    @RequestMapping("/driverform")
    public String driverform(){
        return "earthwork/driverform";
    }

    @RequestMapping("/revenue")
    public String revenue(){
        return "earthwork/revenue";
    }

    @RequestMapping("/revenueform")
    public String revenueform(){
        return "earthwork/revenueform";
    }

    @RequestMapping("/customer")
    public String customer(){
        return "earthwork/customer";
    }

    @RequestMapping("/customerform")
    public String customerform(){
        return "earthwork/customerform";
    }

    @RequestMapping("/transportation")
    public String transportation(){
        return "earthwork/transportation";
    }

    @RequestMapping("/transportationform")
    public String transportationform(){
        return "earthwork/transportationform";
    }

    @RequestMapping("/income")
    public String income(){
        return "earthwork/income";
    }

    @RequestMapping("/incomeform")
    public String incomeform(){
        return "earthwork/incomeform";
    }

    @RequestMapping("/expenditure")
    public String expenditure(){
        return "earthwork/expenditure";
    }

    @RequestMapping("/expenditureform")
    public String expenditureform(){
        return "earthwork/expenditureform";
    }

    @RequestMapping("/advance")
    public String advance(){
        return "earthwork/advance";
    }

    @RequestMapping("/advanceform")
    public String advanceform(){
        return "earthwork/advanceform";
    }

    @RequestMapping("/clearSession")
    public void initSession(HttpServletRequest request){
        Enumeration em=request.getSession().getAttributeNames();
        while (em.hasMoreElements()) {
            request.getSession().removeAttribute(em.nextElement().toString());
        }

    }

}
