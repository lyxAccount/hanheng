package com.example.interfacedemo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName("account")
public class Account {
    @TableId
    private String id;

    private String money;

    private Integer uid;
}
