package com.example.interfacedemo.controller.service.impl;

import com.example.interfacedemo.controller.pojo.UserMine;
import com.example.interfacedemo.controller.service.UserMineService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("user02")
public class UserServiceImpl02 implements UserMineService {


    @Override
    public String test() {
        return "user02";
    }

    @Override
    public List<UserMine> getUser() {
        return null;
    }
}
