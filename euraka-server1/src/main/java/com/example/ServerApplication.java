package com.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@EnableEurekaServer
@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
	    new SpringApplicationBuilder(ServerApplication.class).web(true).run(args);
	}
	
//	@Resource
//	private Sender sender;
//
//    @Override
//    public void run(String... arg0) throws Exception {
//        while(true){
//            sender.send();
//            sender.sendHi();
//        }
//    }
}
