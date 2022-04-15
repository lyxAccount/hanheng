package com.example.interfacedemo.controller.service.impl;

import com.alibaba.excel.EasyExcel;
import com.example.interfacedemo.controller.lister.StudentExcelListener;
import com.example.interfacedemo.controller.pojo.Student;
import com.example.interfacedemo.controller.service.ExcelService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {
    @Override
    public List<Student> read(MultipartFile file, ExcelService excelService) {
        List<Student> studentList = new ArrayList<>();
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, Student.class,new StudentExcelListener(excelService,studentList)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("学生的数量为"+studentList.size());
        return studentList;
    }
}
