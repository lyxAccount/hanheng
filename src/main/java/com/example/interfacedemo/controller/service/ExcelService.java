package com.example.interfacedemo.controller.service;

import com.example.interfacedemo.controller.pojo.Student;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface ExcelService {
    /**
     * 读取excel内容并存入数据库
     * @param file
     * @param excelService
     * @return
     */
    List<Student> read(MultipartFile file, ExcelService excelService);

    void export(HttpServletResponse response) throws IOException;

    void exportMerge(HttpServletResponse response);
}
