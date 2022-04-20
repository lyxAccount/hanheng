package com.example.interfacedemo.designMode.mode2.inter;

import com.example.interfacedemo.designMode.inter.MathOperation;

import java.util.Optional;

/**
 * 工厂方法模式(mode2文件夹中使用到了简单工厂中的几个实现类，本打算一个文件夹中是一个模式)
 */
public interface MathFactoryInterface {
    Optional<MathOperation> getOperation(String operator);
}

