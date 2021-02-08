package com.tz.client2.feign.callback;

import com.tz.client2.feign.service.Client1Service;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * @author https://github.com/TianPuJun @醉鱼
 * @ClassName ServiceFallback
 * @Description
 * @Date 19:33 2021/2/8
 **/
@Slf4j
@Component
public class ServiceFallback implements FallbackFactory<Client1Service> {
    @Override
    public Client1Service create(Throwable throwable) {
        String msg = throwable == null ? "" : throwable.getMessage();
        if (!StringUtils.isEmpty(msg)) {
            log.error("feign 接口错误[{}]",msg);
        }else{
            log.error("feign 接口空指针[{}]", Arrays.toString(throwable.getStackTrace()));
        }
        return new Client1Service() {
            @Override
            public String hello(String name) {
                return "发生错误返回默认信息："+name;
            }

        };
    }
}
