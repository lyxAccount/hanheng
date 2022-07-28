package com.example.interfacedemo.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class ExcelDto {

    /**
     * 货物名称
     */
    @ExcelProperty(value ="货物名称")
    private String goodsName;

    /**
     * 货物代码
     */
    @ExcelProperty(value ="货物代码")
    private String productCode;

    /**
     * 生产公司
     */
    @ExcelProperty(value ="生产公司")
    private String createCompany;
}
