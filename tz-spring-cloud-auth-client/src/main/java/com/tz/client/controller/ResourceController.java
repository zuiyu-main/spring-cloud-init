package com.tz.client.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tz
 * @Classname RescourseController
 * @Description 测试资源控制
 * @Date 2019-12-28 11:58
 */
@RestController
public class ResourceController {
    /**
     * PreAuthorize 拥有p1权限即可访问r1
     * @return
     */
    @GetMapping("/r/r1")
    @PreAuthorize("hasAnyAuthority('p1')")
    public String r1(){
        return "访问资源r1";
    }
    @GetMapping("/r/r2")
    @PreAuthorize("hasAnyAuthority('p2')")
    public String r2(){
        return "访问资源r2";
    }
}
