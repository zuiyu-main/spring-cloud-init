##  feign 调用demo
* 异常 failed and no fallback available
解决： 创建callback
```text
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
```

* 创建带保留信息的异常类
```text
public class KeepErrMsgConfiguration {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new UserErrorDecoder();
    }
    /**
     * 自定义错误
     */
    @Slf4j
    public static class UserErrorDecoder implements ErrorDecoder {
        @Override
        public Exception decode(String methodKey, Response response) {
            Exception exception = null;
            try {
                // 获取原始的返回内容
                String json = Util.toString(response.body().asReader());
                exception = new RuntimeException(json);
                log.error("发生异常[{}]",json);

            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
            return exception;
        }
    }
}
```

* 不发生熔断的异常信息类，返回HystrixBadRequestException

```text
public class NotBreakerConfiguration {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new UserErrorDecoder();
    }
    /**
     * 自定义错误
     */
    @Slf4j
    public static class UserErrorDecoder implements ErrorDecoder {
        @Override
        public Exception decode(String methodKey, Response response) {
            Exception exception = null;
            try {
                // 获取原始的返回内容
                String json = Util.toString(response.body().asReader());
                exception = new RuntimeException(json);
                log.error("发生异常[{}]",json);
                exception = new HystrixBadRequestException("自定义异常信息:"+json);

            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
            return exception;
        }
    }
}
```
* 配置feign token
```text
@Configuration
public class FeignConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //添加token
        requestTemplate.header("Authorization", request.getHeader("Authorization"));
    }
}
```

* feign path 配置 
对应client context-path

```text
@FeignClient(value = "client-service",path = "/client",
        fallbackFactory = ServiceFallback.class,
        configuration = {NotBreakerConfiguration.class})
```
