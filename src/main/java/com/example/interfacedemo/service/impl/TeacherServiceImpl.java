package com.example.interfacedemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.interfacedemo.entity.Teacher;

import com.example.interfacedemo.mapper.TeacherMapper;
import com.example.interfacedemo.service.TeacherService;

import org.springframework.stereotype.Service;

/**
* @author S
* @description 针对表【teacher】的数据库操作Service实现
* @createDate 2022-04-08 09:55:24
*/
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
    implements TeacherService {

}




