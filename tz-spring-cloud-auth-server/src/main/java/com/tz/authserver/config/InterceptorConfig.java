package com.tz.authserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author https://github.com/TianPuJun @醉鱼
 * @ClassName InterceptorConfig
 * @Description
 * @Date 16:54 2020/10/22
 **/
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
    @Override
    protected  void  addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler( "/**" ).addResourceLocations( "classpath:/static/" );
        super .addResourceHandlers(registry);
    }
}
