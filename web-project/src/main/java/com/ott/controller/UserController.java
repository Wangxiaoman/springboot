package com.ott.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ott.ResultJson;

@Controller
public class UserController {
    
    @GetMapping("/page")
    public String page(){
        System.out.println("11111");
        return "/WEB-INF/jsp/page.jsp";
    }
    
    @RequestMapping("/test")
    @ResponseBody
    public ResultJson getResult(){
        ResultJson result = new ResultJson(1, "123123","哈哈哈");
        return result;
    }
    
    
    @RequestMapping("/teststring")
    @ResponseBody
    public String getString(){
        return "哈哈哈哈";
    }
}


