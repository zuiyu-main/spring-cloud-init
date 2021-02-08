package com.tz.client2.feign.service;

import com.tz.client2.feign.callback.ServiceFallback;
import com.tz.client2.feign.config.NotBreakerConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author https://github.com/TianPuJun @醉鱼
 * @ClassName Client1Service
 * @Date 18:40 2021/2/8
 **/
@FeignClient(value = "client-service",path = "/client",
        fallbackFactory = ServiceFallback.class,
        configuration = {NotBreakerConfiguration.class})
public interface Client1Service {
    @GetMapping("/hello")
    String hello(@RequestParam String name);
}
