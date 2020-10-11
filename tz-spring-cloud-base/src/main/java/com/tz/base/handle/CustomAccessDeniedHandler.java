package com.tz.base.handle;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tz.base.model.ResultBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author https://github.com/TianPuJun @醉鱼
 * @ClassName CustomAccessDeniedHandler
 * @Description 权限不足异常
 * @Date 11:51 2020/10/11
 **/
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        ResultBean res = new ResultBean();
        res.setCode(HttpStatus.UNAUTHORIZED.value());
        res.setMsg("权限不足");
        res.setData(e.getMessage());
        res.setPath(httpServletRequest.getServletPath());
        res.setTimestamp(System.currentTimeMillis());
        ObjectMapper mapper = new ObjectMapper();
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.getWriter().write(mapper.writeValueAsString(res));
    }
}
