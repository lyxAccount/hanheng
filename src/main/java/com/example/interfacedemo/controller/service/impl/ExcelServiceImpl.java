package com.example.interfacedemo.controller.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.example.interfacedemo.controller.lister.StudentExcelListener;
import com.example.interfacedemo.controller.pojo.Student;
import com.example.interfacedemo.controller.service.ExcelService;
import com.example.interfacedemo.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ExcelServiceImpl implements ExcelService {
    @Override
    public void export(HttpServletResponse response) throws IOException {
        List<User> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            User user = new User(i, UUID.randomUUID().toString(),"123","ADMIN");
            list.add(user);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        String date = sdf.format(new Date());
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("test"+date, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        LongestMatchColumnWidthStyleStrategy longestMatchColumnWidthStyleStrategy =
                new LongestMatchColumnWidthStyleStrategy();
        EasyExcel.write(response.getOutputStream(), User.class)
                .sheet("test")
                .registerWriteHandler(longestMatchColumnWidthStyleStrategy)
                .doWrite(list);

    }

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
