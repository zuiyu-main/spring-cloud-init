package com.tz.sso.config;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author tz
 * @Classname LoginConfig
 * @Description 登录配置
 * @Date 2020-01-02 10:00
 */
@Configuration
@EnableOAuth2Sso
public class LoginConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .antMatcher("/dashboard/**").authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/dashboard/logout").permitAll()
                .logoutSuccessUrl("/");
    }




    //    @Bean
//    public FilterRegistrationBean oauth2ClientFilterRegistration(
//            OAuth2ClientContextFilter filter) {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(filter);
//        registration.setOrder(-100);
//        return registration;
//    }
}
