package com.example.interfacedemo.controller.sys;

import com.example.interfacedemo.entity.User;
import com.example.interfacedemo.mapper.UserMapper;
import com.example.interfacedemo.service.UserService;
import com.example.interfacedemo.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
@Api(tags = "用户")
@RestController
public class UserController {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userDao;

    @GetMapping("/get")
    public User getUsers(@RequestParam String username){
        return null;
    }

    @ApiOperation(value = "登录")
    @GetMapping("/login")
    public Map login(@RequestBody User loginParam, HttpServletRequest request) {
        return userService.login(loginParam.getUsername(), loginParam.getPassword(), request);
    }

    @GetMapping("/get-user")
    public User getUser(@RequestParam String username){
        return userService.getUserInfo(username);
    }


    @PreAuthorize("hasAnyRole('admin')") // 只能admin角色才能访问该方法,注意，这里是区分大小写的
    @GetMapping("/admin")
    public String user(){
        return "user角色访问";
    }

    @PostMapping("/register")
    public String registerUser(@RequestBody User registerUser) {
        userService.insertUser(registerUser);

        return "success";
    }

    @GetMapping("/auth")
    public String auth(){
        return "认证成功才可以访问";
    }

    @GetMapping("/redis/test")
    public String redisTets(){
        User user = new User();
        user.setUsername("redis_test");
        redisUtil.set("user",user);
        Object object = redisUtil.get("user");
        return object.toString();
    }

    @GetMapping("/one")
    public void testOneToMany() {
        userService.testOnetomany();
    }
}
