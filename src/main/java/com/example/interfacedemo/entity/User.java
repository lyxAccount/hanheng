package com.example.interfacedemo.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    @ExcelIgnore
    private Integer id;

    /**
     * 
     */
    @TableField("username")
    @ExcelProperty(value = "用户名",index = 1)
    private String username;

    /**
     * 
     */
    @ExcelIgnore
    @TableField("password")
    private String password;


    /**
     * 
     */
    @ExcelIgnore
    private String role;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private List<Account> accountList;


    public User(Integer id,String username,String password,String role){
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }
}