package com.example.interfacedemo.controller.conroller;

import com.example.interfacedemo.entity.Dept;
import com.example.interfacedemo.mapper.DeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptMapper deptMapper;

    /**
     * 测试自身关联的一对多xml写法
     */
    @GetMapping("/get")
    public void testOneToMany() {
        Dept dept = deptMapper.getDeptList("1");
        System.out.println(dept);
//        List<Dept> depts = deptMapper.selectList(null);
    }

    @GetMapping("/test")
    public Dept test(){
        return deptMapper.test("1");
    }
}
