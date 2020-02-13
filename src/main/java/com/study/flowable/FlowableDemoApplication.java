package com.study.flowable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication(proxyBeanMethods = false) // 测试结果：启动正常
@SpringBootApplication // 去掉(proxyBeanMethods = false) 测试，启动正常
public class FlowableDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowableDemoApplication.class, args);
    }
}
