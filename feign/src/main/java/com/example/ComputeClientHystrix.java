package com.example;

import org.springframework.stereotype.Component;

@Component
public class ComputeClientHystrix implements ComputeClient {

    @Override
    public Integer add(int a, int b) {
        return -9999;
    }

}
