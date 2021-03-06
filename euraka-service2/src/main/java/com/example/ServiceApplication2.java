package com.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ServiceApplication2 {

    public static void main(String[] args) {
        new SpringApplicationBuilder(ServiceApplication2.class).web(true).run(args);
    }
}
