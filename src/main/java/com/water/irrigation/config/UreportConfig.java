package com.water.irrigation.config;

import javax.servlet.Servlet;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;


import com.bstek.ureport.console.UReportServlet;

/**
 * @Title UreportConfig
 * @Description TODO Ureport2 配置类
 * @Author licl
 * @Created 2019/11/3 12:30
 **/
@ImportResource("classpath:uReport2/spring-ureport2.xml")
@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = "com.water.irrigation")
public class UreportConfig {
    @Bean
    public ServletRegistrationBean<Servlet> buildUreportServlet(){
        return new ServletRegistrationBean<Servlet>(new UReportServlet(), "/ureport/*");
    }

}
