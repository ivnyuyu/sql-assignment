package com.ivan.yuyuk.sqlassignment.controller;

import com.ivan.yuyuk.sqlassignment.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class Test {

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @PostMapping("/test")
    public String getData(User user){
        System.out.println(user);
        return "redirect:/test";
    }
}
