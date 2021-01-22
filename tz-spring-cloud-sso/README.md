# sso单点登录
* [官网sso](https://projects.spring.io/spring-cloud/spring-cloud.html#_oauth2_single_sign_on)
* [registered-redirect-uri 等相关配置](https://docs.spring.io/spring-security/oauth/apidocs/org/springframework/security/oauth2/client/token/grant/redirect/AbstractRedirectResourceDetails.html)
* [security 配置解析，挺详细的介绍](https://blog.ityuan.com/1055)
* [springboot security ](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-security-oauth2-client)

# 配置拦截url，申请token,@EnableOAuth2Sso开启sso
```
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
    }
```
# 暂定登录成功跳转url，和数据库客户端信息保持一致
```
@RestController
public class IndexController {
    @RequestMapping("/loginSuccess")
    public String loginSuccess(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        String code = parameterMap.get("code")[0];
        String state = parameterMap.get("state")[0];
        System.out.println(code);
        System.out.println(state);
        // 根据code申请token
        // 此处可跳转网页，路由等，有前端发起请求token


        return "login Success";
    }
}
```
# yml配置oauth
```
security:
  oauth2:
    sso:
      loginPath: /dashboard/login
    client:
      accessTokenUri: http://localhost:8762/auth/oauth/token
      userAuthorizationUri: http://localhost:8762/auth/oauth/authorize
      clientId: sso1
      clientSecret: secret
      scope: ROLE_API
      registered-redirect-uri: http://localhost:8780/sso/loginSuccess
      #      pre-established-redirect-uri: http://localhost:8762/client/r/r1
      # 默认true，是否使用默认重定向url，
      use-current-uri: false
      authorized-grant-types: authorization_code
      id: sso1
    resource:
      jwt:
        # jwt 认证的解密的key，与auth服务加密key一致
        key-value: tz_admin
      id: sso1
      serviceId: sso1
      user-info-uri: http://localhost:8780/dashboard/user
```
# 注意配置使用
* auth-server token签名设置
* 回调地址
