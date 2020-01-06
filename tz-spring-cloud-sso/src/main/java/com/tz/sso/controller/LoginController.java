package com.tz.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.Map;

/**
 * @author tz
 * @Classname LoginController
 * @Description
 * @Date 2020-01-02 10:03
 */
@RestController
@RequestMapping("/dashboard")
public class LoginController {

    @RequestMapping("/message")
    public Map<String, Object> dashboard() {
        return Collections.<String, Object> singletonMap("message", "Yay!");
    }

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
    }
    @RequestMapping("/logout")
    public String logout() {
        return "success logout ";
    }
    @RequestMapping("/")
    public String home() {
       return  "Hello World";
    }
    @Controller
    public static class LoginErrors {

        @RequestMapping("/dashboard/login")
        @ResponseBody
        public String dashboard() {
            return "redirect:/#/";
        }

    }
}
