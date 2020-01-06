package com.tz.zuul.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author tz
 * @Classname WebSecurityConfig
 * @Description
 * @Date 2019-12-24 16:45
 */
@Slf4j
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    /**
     *  安全拦截机制
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("WebSecurityConfig 进入拦截");
        // 关闭csrf
        http.csrf().disable()
                // 放行所有的url
                .authorizeRequests()
                .antMatchers("/**").permitAll();
    }
}
