package com.example.interfacedemo.controller.sys;

import com.example.interfacedemo.controller.annonice.TestAop;
import com.example.interfacedemo.controller.pojo.Student;
import com.example.interfacedemo.controller.pojo.UserMine;
import com.example.interfacedemo.controller.service.AmrService;
import com.example.interfacedemo.controller.service.UserMineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user/mine")
public class UserMineController {

//    @Autowired
//    @Qualifier("user01")
    @Resource(name = "user02")
    private UserMineService userService;

    @Autowired
    private AmrService amrService;

    @GetMapping("/test")
    public String test001(){
        String result = userService.test();
        return result;
    }

    @PostMapping("/amr")
    public Object arm(){
        amrService.amr();
        return "0";
    }

    @PostMapping("/io")
    public void testIo(HttpServletResponse response)throws Exception{
        amrService.testIo(response);
    }

    @TestAop(opreation = "aop注解测试")
    @PostMapping("/list")
    public List<Student> getList(){
        List<Student> list =new ArrayList<>();
        Student student = new Student("dd",5);
        Student student1 = new Student("cc",6);
        list.add(student);
        list.add(student1);
        return list;
    }

    @GetMapping("/getUsers")
    public List<UserMine> get(){
        return userService.getUser();
    }
}
