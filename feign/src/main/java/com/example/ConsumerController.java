package com.example;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {
    @Resource
    private ComputeClient computeClient;
    
    @RequestMapping("/add")
    public Integer add(){
        return computeClient.add(10, 20);
    }
    
}
