package com.example.interfacedemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@MapperScan("com.example.interfacedemo.mapper")
@SpringBootApplication
@EnableSwagger2
public class InterfaceDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterfaceDemoApplication.class, args);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        // 使用BCrypt加密密码
        return new BCryptPasswordEncoder();
    }
}
