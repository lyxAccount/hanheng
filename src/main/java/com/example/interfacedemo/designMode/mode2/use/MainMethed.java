package com.example.interfacedemo.designMode.mode2.use;

import com.example.interfacedemo.designMode.inter.MathOperation;
import com.example.interfacedemo.designMode.mode2.impl.AddFactory;
import com.example.interfacedemo.designMode.mode2.impl.DivideFactory;
import com.example.interfacedemo.designMode.mode2.impl.MultiplyFactory;
import com.example.interfacedemo.designMode.mode2.impl.SubFactory;
import com.example.interfacedemo.designMode.mode2.inter.MathFactoryInterface;

import java.util.Optional;

/**
 *  为了解决第一种简单工厂模式的缺陷，产生了工厂方法模式，把工厂方法再次进行抽象，
 *
 * 为不同的实现类，提供不同的工厂，通过实现抽象工厂接口类的方法，实现不同工厂获取
 *
 * 业务实现类的不同实例，调用的时候，通过判断，使用不同的工厂（在简单工厂模式基础上）
 */
public class MainMethed {

    public String run(String type){
        double result;
        MathFactoryInterface factory = getFactory(type);
        MathOperation operation = factory.getOperation(type).orElseThrow(() ->
                new IllegalArgumentException("未知的操作"));

        result = operation.apply(1, 2);
        return String.valueOf(result);
    }


    private MathFactoryInterface getFactory(String operator){
        MathFactoryInterface result = null;
        if("add".equals(operator)){
            result = new AddFactory();
        }else if("sub".equals(operator)){
            result = new SubFactory();
        }else if("multiply".equals(operator)){
            result = new MultiplyFactory();
        }else if("divide".equals(operator)){
            result = new DivideFactory();
        }
        return Optional.ofNullable(result).orElseThrow(() -> new IllegalArgumentException("未知的操作"));
    }
}
