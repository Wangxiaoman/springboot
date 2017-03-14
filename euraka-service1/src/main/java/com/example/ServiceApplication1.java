package com.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

import io.zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableZipkinServer
public class ServiceApplication1 {

	public static void main(String[] args) {
	    new SpringApplicationBuilder(ServiceApplication1.class).web(true).run(args);
	}
}
