package com.sagiri;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync//设置支持异步操作
public class DemoHelloApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoHelloApplication.class, args);
    }
}
