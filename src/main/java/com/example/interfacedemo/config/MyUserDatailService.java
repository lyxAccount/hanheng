package com.example.interfacedemo.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.interfacedemo.entity.User;
import com.example.interfacedemo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDatailService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userInfo = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getUsername, username).last("limit 1"));
        if (userInfo == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // 得到用户角色
        String role = userInfo.getRole();

        // 角色集合
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 角色必须以`ROLE_`开头，数据库中没有，则在这里加
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role));

        return new org.springframework.security.core.userdetails.User(
                userInfo.getUsername(),
                // 因为数据库是明文，所以这里需加密密码
                userInfo.getPassword(),
                authorities
        );
    }
}
