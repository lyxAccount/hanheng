package com.example.interfacedemo.mapper;

import com.example.interfacedemo.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
* @author S
* @description 针对表【user】的数据库操作Mapper
* @createDate 2022-04-08 09:55:24
* @Entity com.example.interfacedemo.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




