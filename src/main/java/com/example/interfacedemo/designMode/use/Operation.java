package com.example.interfacedemo.designMode.use;

import com.example.interfacedemo.designMode.factory.MathFactory;
import com.example.interfacedemo.designMode.factory.MathFactory02;
import com.example.interfacedemo.designMode.factory.MathFactory03;
import com.example.interfacedemo.designMode.inter.MathOperation;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

/**
 * 实用类
 */
public class Operation {
    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        //orElseThrow()如果存在该值，返回包含的值，否则抛出由 Supplier 继承的异常
        //除了null,其他空类型的数据都属于存在该值
        MathOperation mathOperation = MathFactory.getOperation("add").orElseThrow(() ->
                new IllegalArgumentException("未知的操作"));
        double apply = mathOperation.apply(1, 2);
        System.out.println(apply);

        MathOperation add = MathFactory02.getOperation("add").get();
        double apply1 = add.apply(1, 2);
        System.out.println(apply1);

        MathOperation operation = MathFactory03.getOperation("add").get();
        double apply2 = operation.apply(1, 2);
        System.out.println(apply2);

    }
}
