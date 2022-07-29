package com.example.interfacedemo.controller.conroller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobTest {

    @PostMapping("/testJob")
    public void test(){
        System.out.println("99999999");
    }
}
