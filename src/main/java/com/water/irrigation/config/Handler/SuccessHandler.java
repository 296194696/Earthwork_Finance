package com.water.irrigation.config.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.water.irrigation.utils.ResponseBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp,
                                        Authentication authentication) throws IOException, ServletException {

        ResponseBean ok =ResponseBean.ok("登录成功！");

        resp.setContentType("application/json;charset=utf-8");

        PrintWriter out = resp.getWriter();

        out.write(new ObjectMapper().writeValueAsString(ok));

        out.flush();

        out.close();

    }

}
