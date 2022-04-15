package com.example.interfacedemo.controller.service.impl;

import com.example.interfacedemo.controller.pojo.UserMine;
import com.example.interfacedemo.controller.service.UserMineService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("user01")
public class UserServiceImpl01 implements UserMineService {

    @Override
    public String test() {
        return "user01";
    }

    @Override
    public List<UserMine> getUser() {
        return null;
    }
}
