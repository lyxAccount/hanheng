package com.example.interfacedemo.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Dept {
    private String id;

    private String pid;

    private String name;

    private List<Dept> children;
}
