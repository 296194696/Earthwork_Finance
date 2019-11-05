package com.water.irrigation.config.Handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.water.irrigation.utils.ResponseBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class FailureHandler implements AuthenticationFailureHandler {

    @Override

    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse resp,
                                        AuthenticationException e) throws IOException, ServletException {

        ResponseBean error = ResponseBean.paramError("登录失败");

        resp.setContentType("application/json;charset=utf-8");

        PrintWriter out = resp.getWriter();

        out.write(new ObjectMapper().writeValueAsString(error));

        out.flush();

        out.close();

    }

}
