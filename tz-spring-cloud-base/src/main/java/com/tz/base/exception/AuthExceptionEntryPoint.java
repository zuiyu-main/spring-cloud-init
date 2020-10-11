package com.tz.base.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tz.base.model.ResultBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author https://github.com/TianPuJun @醉鱼
 * @ClassName AuthExceptionEntryPoint
 * @Description token 异常
 * @Date 11:44 2020/10/11
 **/
public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ResultBean res = new ResultBean();
        Throwable cause = e.getCause();
        if(cause instanceof InvalidTokenException) {
            res.setCode(HttpStatus.UNAUTHORIZED.value());
            res.setMsg("无效的token");
        }else{
            res.setCode(HttpStatus.UNAUTHORIZED.value());
            res.setMsg("访问此资源需要完全的身份验证");
        }
        res.setData(e.getMessage());
        res.setPath(httpServletRequest.getServletPath());
        res.setTimestamp(System.currentTimeMillis());
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(httpServletResponse.getOutputStream(), res);
        } catch (Exception json) {
            throw new ServletException();
        }
    }
}
