package com.tz.authserver.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Arrays;

/**
 * @author https://github.com/TianPuJun @醉鱼
 * @ClassName LoginController
 * @Description
 * @Date 16:44 2020/10/22
 **/
@Controller
public class LoginController {

    @GetMapping("/loginSuccess")
    @ResponseBody
    public String login(){
        return "say hello zuiyu!";
    }

    @GetMapping("/oauth/user")
    @ResponseBody
    public String user() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName() + Arrays.toString(authentication.getAuthorities().toArray());
    }
    @GetMapping("/user")
    @ResponseBody
    public Principal getCurrentUser(Principal principal) {
        return principal;
    }
}
