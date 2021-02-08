package com.tz.client2.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author tz
 * @Classname WebSecurityConfig
 * @Description web安全控制
 * @Date 2019-12-28 19:21
 */
@SpringBootConfiguration
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                // 访问/r/r1需要p1
//                .antMatchers("/r/r1").hasAuthority("p1")
                // 访问/r/r2需要p2
//                .antMatchers("/r/r2").hasAuthority("p2")
                // 拦截所有/r/**请求
                .antMatchers("/r/**").authenticated()
                // 其他放行
                .anyRequest().permitAll();
    }
}
