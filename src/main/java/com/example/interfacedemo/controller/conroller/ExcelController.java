package com.example.interfacedemo.controller.conroller;

import com.example.interfacedemo.controller.pojo.Student;
import com.example.interfacedemo.controller.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    @PostMapping("/read")
    public List<Student> excelTest(@RequestParam("file") MultipartFile file){
        List<Student> studentList = excelService.read(file,excelService);
        return studentList;
    }

    @PostMapping("/export")
    public String exportExcel(HttpServletResponse response) throws IOException {
        excelService.export(response);
        return "success";
    }
}
