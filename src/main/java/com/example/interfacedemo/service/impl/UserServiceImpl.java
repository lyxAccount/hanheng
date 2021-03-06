package com.example.interfacedemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.example.interfacedemo.config.MyUserDatailService;
import com.example.interfacedemo.entity.User;
import com.example.interfacedemo.mapper.UserMapper;
import com.example.interfacedemo.service.UserService;
import com.example.interfacedemo.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author S
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-04-08 09:55:24
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Autowired
    private UserMapper userDao;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MyUserDatailService myUserDatailService;

    @Override
    public User getUserInfo(String username) {
        return userDao.selectOne(new QueryWrapper<User>().lambda().eq(User::getUsername,username).last("limit 1"));
    }

    @Override
    public void insertUser(User registerUser) {
        User user = new User();
        user.setUsername(registerUser.getUsername());
        user.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        user.setRole(registerUser.getRole());
        userDao.insert(user);
    }

    @Override
    public Map login(String username, String password, HttpServletRequest request) {
        Map<String, Object> tokenMap = new HashMap<>();
        //登录
        UserDetails userDetails = myUserDatailService.loadUserByUsername(username);
        if (null == userDetails || !passwordEncoder.matches(password, userDetails.getPassword())) {
            tokenMap.put("code", 400);
            tokenMap.put("message", "密码错误");
            return tokenMap;
        }
        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails
                , null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);

        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        tokenMap.put("code", 200);
        tokenMap.put("message", "登录成功");
        return tokenMap;
    }

    @Override
    public List<User> testOnetomany() {
        List<User> list =  userDao.testOneToMany();
        return list;
    }
}




