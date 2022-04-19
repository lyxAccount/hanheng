package com.example.interfacedemo.designMode.factory;

import com.example.interfacedemo.designMode.inter.MathOperation;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

/**
 * 简单工厂模式（反射）
 */
public class MathFactory03 {

    private MathFactory03() {
        //do nothing
    }

    /**
     * 获得具体的操作类型
     */
    public static Optional<MathOperation> getOperation(String operator) throws ClassNotFoundException,
            IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        //因为类名首字母大写，所以转换操作类型为类名格式
        String operate = operator.substring(0,1).toUpperCase() + operator.substring(1).toLowerCase();
        Class<?> operation = Class.forName("com.example.interfacedemo.designMode.impl." + operate+"Operation");
        return Optional.of((MathOperation)operation.getDeclaredConstructor().newInstance());
    }
}
