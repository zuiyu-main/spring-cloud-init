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
