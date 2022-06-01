package com.example.interfacedemo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@TableName("dept")
@NoArgsConstructor
@AllArgsConstructor
public class Dept {
    @TableId
    private String id;

    @TableField("pid")
    private String pid;

    @TableField("name")
    private String name;

    @TableField(exist = false)
    private List<Dept> children;
}
