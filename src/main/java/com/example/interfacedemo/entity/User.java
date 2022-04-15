package com.example.interfacedemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    @TableField("username")
    private String username;

    /**
     * 
     */
    @TableField("password")
    private String password;


    /**
     * 
     */
    private String role;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;



}