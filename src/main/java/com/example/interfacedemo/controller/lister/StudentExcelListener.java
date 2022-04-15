package com.example.interfacedemo.controller.lister;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.interfacedemo.controller.pojo.Student;
import com.example.interfacedemo.controller.service.ExcelService;
import lombok.SneakyThrows;

import java.util.List;

public class StudentExcelListener extends AnalysisEventListener<Student> {

    public ExcelService excelService;

    public List<Student> list;

    public StudentExcelListener(ExcelService excelService,List<Student> list) {
        this.excelService = excelService;
        this.list = list;
    }


    public StudentExcelListener() {

    }

    /**
     * 遍历每一行的记录
     * @param student
     * @param analysisContext
     */
    @SneakyThrows
    @Override
    public void invoke(Student student, AnalysisContext analysisContext) {
        if (student == null) {
            throw new Exception("文件数据为空");
        }

//        Student stu = new Student();
//        stu.setName(student.getName());
//        stu.setAge(student.getAge());
        list.add(student);
        System.out.println(list.size());
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
