package com.example.interfacedemo.designMode.mode3.impl;

import com.example.interfacedemo.designMode.mode3.inter.IDeveloper;

public class AliDeveloper implements IDeveloper {
    @Override
    public String work() {
        return "我是阿里开发人员，我的工作是：为阿里服务！";
    }

    @Override
    public String skill() {
        return "我是阿里开发人员，我的技能是：java、python、vue、react、js、c、c++、c#......无所不能！";
    }
}
