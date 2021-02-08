package com.tz.client2.controller;

import com.alibaba.fastjson.JSON;
import com.oracle.tools.packager.Log;
import com.tz.base.model.UserDto;
import com.tz.client2.feign.service.Client1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tz
 * @Classname ResourceController
 * @Description 测试资源控制
 * @Date 2019-12-28 11:58
 */
@RestController
public class ResourceController {

    @Value("${server.port}")
    public String port;
    @Autowired
    private Client1Service client1Service;
    /**
     * PreAuthorize 拥有p1权限即可访问r1
     * @return
     */
    @GetMapping("/r/r1")
    @PreAuthorize("hasAnyAuthority('p1')")
    public String r1() {

//        UserDto userDto =(UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDto userDto = JSON.parseObject(principal.toString(), UserDto.class);
        return userDto.getUsername() + "访问client2资源r1";
    }
    @GetMapping("/r/r2")
    @PreAuthorize("hasAnyAuthority('p2')")
    public String r2(){
        return "访问资源r2";
    }

    @GetMapping("/test")
    public String test(){
        SecurityContext context = SecurityContextHolder.getContext();
        return "ccc"+port;
    }

    @GetMapping("/hello")
    public String hello(){
        String client2 = client1Service.hello("client2");
        Log.info(String.format("feign client1 return val [%s]",client2));
        return client2;
    }

}
