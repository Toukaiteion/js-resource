package com.hseea.jsresource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class JsResourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JsResourceApplication.class, args);
    }

}
