package com.example.interfacedemo.designMode.mode3.impl;

import com.example.interfacedemo.designMode.mode3.inter.IProductor;

public class AliProductor implements IProductor {
    @Override
    public String work() {
        return  "我是阿里产品，我的工作是：为阿里提供优秀的产品！";
    }

    @Override
    public String skill() {
        return "我是阿里产品，我的技能是：需求与利益的平衡能力，缜密的逻辑思维和较高的业务理解能力！";
    }
}
