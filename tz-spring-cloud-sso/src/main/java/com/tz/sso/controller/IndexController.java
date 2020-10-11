package com.tz.sso.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author tz
 * @Classname IndexController
 * @Description
 * @Date 2020-01-02 15:41
 */
@RestController
public class IndexController {
    @RequestMapping("/loginSuccess")
    public String loginSuccess(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String code = parameterMap.get("code")[0];
        String state = parameterMap.get("state")[0];
        System.out.println(code);
        System.out.println(state);
        // 根据code申请token
        // 此处可跳转网页，路由等，有前端发起请求token


        return "login Success";
    }
}
