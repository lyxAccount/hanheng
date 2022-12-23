package com.example.interfacedemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.interfacedemo.entity.Dept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeptMapper extends BaseMapper<Dept> {
    Dept getDeptList(String id);

    Dept test(String id);

    List<Dept> getListByPiD(String id);

    List<Dept> getList();
}
