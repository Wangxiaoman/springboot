package com.example;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ComputeService {
    @Resource
    private RestTemplate restTemplate;
    
    @HystrixCommand(fallbackMethod="callback")
    public String add(){
        return restTemplate.getForEntity("http://COMPUTE-SERVICE/add?a=10&b=20",String.class).getBody();
    }
    
    public String callback(){
        return "error";
    }
    
}
