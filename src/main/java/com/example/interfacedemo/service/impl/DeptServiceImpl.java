package com.example.interfacedemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.interfacedemo.entity.Dept;
import com.example.interfacedemo.mapper.DeptMapper;
import com.example.interfacedemo.service.DeptService;
import org.springframework.stereotype.Service;

@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {
}
