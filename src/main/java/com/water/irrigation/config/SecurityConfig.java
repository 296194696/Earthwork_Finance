package com.water.irrigation.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.water.irrigation.entity.sys.user.SysUser;
import com.water.irrigation.service.sys.user.SysUserService;
import com.water.irrigation.utils.BaseMsg;
import com.water.irrigation.utils.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/layuiadmin/**","/my/**","/register_page","/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .anyRequest()
//                .authenticated()
                .antMatchers("/checkNameIsExistOrNot","/register_form").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login_page")
//                    .loginProcessingUrl("/doLogin")
                    .defaultSuccessUrl("/index")
                    .permitAll()
                //开启跨域
                .and()
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
//处理跨域请求中的Preflight请求
                .requestMatchers( CorsUtils::isPreFlightRequest ).permitAll();
        // 解决不允许显示在iframe的问题
        http.headers().frameOptions().disable();
        http.headers().cacheControl();

        http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                //ResponseBean respBean = ResponseBean.ok(1000);
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();
                BaseMsg baseMsg=new BaseMsg("1000","登录成功",userDetails);
                //respBean.setBody(baseMsg);
                out.write(new ObjectMapper().writeValueAsString(baseMsg));
                out.flush();
                out.close();
            }
        });
        filter.setAuthenticationFailureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp, AuthenticationException e) throws IOException, ServletException {
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                ResponseBean respBean = ResponseBean.failure(new BaseMsg("1001","用户名字或密码错误！"));
                BaseMsg baseMsg=new BaseMsg("1001","用户名字或密码错误！");
                out.write(new ObjectMapper().writeValueAsString(baseMsg));
                out.flush();
                out.close();
            }
        });
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())//自定义构建
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            //username是用户登录时填的用户名
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                //根据username查询密码，现假设是user登录，从任意数据库查到密码是123
                SysUser sysUser=sysUserService.findUserByUsername(username);
                String password = sysUser.getPassword();
                String role=sysUser.getRole();

                //如果查不到用户名，这里可以抛出UsernameNotFoundException异常
                //根据username查询权限，这里假设从任意位置查到权限是auth
                List<GrantedAuthority> authorities= new ArrayList<GrantedAuthority>();
                authorities.add(new SimpleGrantedAuthority(role));
                //User是系统自带的UserDetails实现类，4个状态其中一个为false就会抛异常
                return new User(username, password, true, true, true, true, authorities);
            }
        };
    }
}
