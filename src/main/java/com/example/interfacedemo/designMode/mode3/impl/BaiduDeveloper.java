package com.example.interfacedemo.designMode.mode3.impl;

import com.example.interfacedemo.designMode.mode3.inter.IDeveloper;

public class BaiduDeveloper implements IDeveloper {
    @Override
    public String work() {
        return  "我是百度开发人员，我的工作是：为百度服务！";
    }

    @Override
    public String skill() {
        return  "我是百度开发人员，我的技能是：人工智能、java、python、vue、react、js、c、c++、c#......我也无所不能！";
    }
}
