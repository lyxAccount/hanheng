package com.example.interfacedemo.service;

import com.example.interfacedemo.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
* @author S
* @description 针对表【user】的数据库操作Service
* @createDate 2022-04-08 09:55:24
*/
public interface UserService {


    User getUserInfo(String username);

    void insertUser(User registerUser);

    Map login(String username, String password, HttpServletRequest request);

    List<User> testOnetomany();
}
