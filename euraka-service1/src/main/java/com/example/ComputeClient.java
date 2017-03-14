package com.example;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="compute-serviceA")
public interface ComputeClient {
    
    @RequestMapping("/add")
    Integer add(@RequestParam("a")int a,@RequestParam("b")int b);
}
