
spring boot2.2.3集成flowable

1、flowable依赖：
<!-- flowable -->
<dependency>
    <groupId>org.flowable</groupId>
    <artifactId>flowable-spring-boot-starter</artifactId>
    <version>6.4.2</version>
</dependency>

2、在yml中配置使用MySQL数据库
spring:
  datasource:
    url: jdbc:mysql://127.0.0.1:3306/flowable?characterEncoding=UTF-8&serverTimezone=GMT%2b8&nullCatalogMeansCurrent=true
    password: root
    username: root

 3、启动类
    @SpringBootApplication(proxyBeanMethods = false)
    public class FlowableDemoApplication {

        public static void main(String[] args) {
            SpringApplication.run(FlowableDemoApplication.class, args);
        }
    }

 4、启动报错### Cause: java.sql.SQLSyntaxErrorException: Table 'flowable.act_ge_property' doesn't exist

   参考博客 ： https://blog.csdn.net/weixin_43770545/article/details/101197861

    解决办法：
    1.yml 配置时
    配置mysql连接时加上：nullCatalogMeansCurrent=true

5、Flowable的ID使用的是UUID，Activiti的ID使用的是int类型的




