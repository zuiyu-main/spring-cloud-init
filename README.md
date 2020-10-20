# spring-cloud version (Greenwich.RELEASE)
## springboot version (2.1.8.RELEASE)

* [auth-client 认证资源服务器](https://github.com/TianPuJun/spring-cloud-init/tree/master/tz-spring-cloud-auth-client)
* [auth-server(oauth2.0) 认证服务器](https://github.com/TianPuJun/spring-cloud-init/tree/master/tz-spring-cloud-auth-server)
* [zuul-service 网关拦截器](https://github.com/TianPuJun/spring-cloud-init/tree/master/tz-spring-cloud-zuul)
* [eureka-discovery 注册中心](https://github.com/TianPuJun/spring-cloud-init/tree/master/tz-spring-cloud-discovery)
* [sso](https://github.com/TianPuJun/spring-cloud-init/tree/master/tz-spring-cloud-sso)

## 参考
README看起来不是很懂的下面放上两个整理之后的链接，可以参考
* [授权服务器搭建](https://mp.weixin.qq.com/s/2KwHxuJtcIjJe_piP1Ve4w)
* [资源服务器搭建](https://mp.weixin.qq.com/s/PfC4H5Sc0QtQgcRVgIsVwg)

## ZipKin 集成 http形式发送数据包

 如果检测不到服务等待反应一会试试
* 本地启动zipkin服务

下载jar包 https://dl.bintray.com/openzipkin/maven/io/zipkin/java/zipkin-server/
启动 java -jar zipkin-server.jar

* 配置文件加入配置，指定zipkin服务地址

```text
  zipkin:
    sender:
      type: web
    base-url: http://localhost:9411/
    service:
      name: 服务名
```
* pom 依赖

```text
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-sleuth</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zipkin</artifactId>
        </dependency>
```
* 浏览器打开http://localhost:9411/ 打开zipkin的管理页面查看

## 基础URL

* 客户端申请授权码,scope 需要与配置中一致，重定向之后会携带一个code
```text
http://127.0.0.1:8763/zuul/auth/oauth/authorize?client_id=c1&response_type=code&scope=all&redirect_uri=http://www.baidu.com
```
*  根据授权码获取token,code 为上一部返回的code
```text
http://127.0.0.1:8763/zuul/auth/oauth/token?client_id=c1&client_secret=secret&grant_type=authorization_code&code=xG776V&redirect_uri=http://www.baidu.com
```

*  密码模式获取token
```text
http://127.0.0.1:8763/zuul/auth/oauth/token?client_id=c1&client_secret=secret&username=tz&password=123&grant_type=password
```
* 客户端模式获取token
```text
http://127.0.0.1:8763/zuul/auth/oauth/token?client_id=c1&client_secret=secret&grant_type=client_credentials
```
* 校验token，获取token详情
```text
127.0.0.1:8763/zuul/auth/oauth/check_token?token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzMSJdLCJ1c2VyX25hbWUiOiJ7XCJmdWxsbmFtZVwiOlwidGVzdFwiLFwiaWRcIjoxLFwicGFzc3dvcmRcIjpcIiQyYSQxMCQ5emc0bHhrc0xFVFVjLzA5eTdydWwucXhUaldZYlMvZGdOV0k1SlpCMXdERE1yaVEzM0UzcVwiLFwidXNlcm5hbWVcIjpcInR6XCJ9Iiwic2NvcGUiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiIsIlJPTEVfQVBJIl0sImV4cCI6MTYwMzE3MDY0MiwiYXV0aG9yaXRpZXMiOlsicDEiXSwianRpIjoiY2M0NGFiZjktNjQwOS00NTc3LThkMTEtMWFhMzNhOTkzMWQxIiwiY2xpZW50X2lkIjoiYzEifQ.8gqdgQ4ohYzduhEz03CFiRs6QXaal0DjD_WBzxXW8sI
```
* 刷新token  
使用postman时需要把Authorization中选择Basic Auth username=clientId，password=client_secret
```text
http://127.0.0.1:8763/zuul/auth/oauth/token?refresh_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsicmVzMSJdLCJ1c2VyX25hbWUiOiJ7XCJmdWxsbmFtZVwiOlwidGVzdFwiLFwiaWRcIjoxLFwicGFzc3dvcmRcIjpcIiQyYSQxMCQ5emc0bHhrc0xFVFVjLzA5eTdydWwucXhUaldZYlMvZGdOV0k1SlpCMXdERE1yaVEzM0UzcVwiLFwidXNlcm5hbWVcIjpcInR6XCJ9Iiwic2NvcGUiOlsiUk9MRV9BRE1JTiIsIlJPTEVfVVNFUiIsIlJPTEVfQVBJIl0sImF0aSI6ImNjNDRhYmY5LTY0MDktNDU3Ny04ZDExLTFhYTMzYTk5MzFkMSIsImV4cCI6MTYwMzQyMjY0MiwiYXV0aG9yaXRpZXMiOlsicDEiXSwianRpIjoiZWI3NTYxZjUtNmE5MS00NjRiLTlkOGItZDRjYmNkNzM5YjA2IiwiY2xpZW50X2lkIjoiYzEifQ.eJVsTiv6AvQmpwgn1Le9vqJiJX49rHeHmZomZqwQ6II&grant_type=refresh_token
```

* 测试资源访问时，请求头携带token

```text
key = Authorization
val = Bearer token
```
