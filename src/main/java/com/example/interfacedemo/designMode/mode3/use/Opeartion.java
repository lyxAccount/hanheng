package com.example.interfacedemo.designMode.mode3.use;

import com.example.interfacedemo.designMode.mode3.factory.IFactory;
import com.example.interfacedemo.designMode.mode3.inter.IDeveloper;
import com.example.interfacedemo.designMode.mode3.inter.IProductor;

import java.lang.reflect.InvocationTargetException;

/**
 * 抽象工厂模式就是将操作归类，然后分别提供接口，同类下的具体事物实现同一个接口。然后抽象一个工厂接口，
 *
 * 按照不同类别，提供不同的待实现工厂方法；再提供具体的工厂实现类，实现抽象的工厂接口，并在不
 *
 * 同的方法（同一类事物的获取方法）中根据入参返回同类事物中具体的事物，最后给到调用者执行。
 */
public class Opeartion {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        String result = "";
        String operator = "ali";
        //获得具体的工厂（反射）
        String operate = operator.substring(0,1).toUpperCase() + operator.substring(1).toLowerCase();
        Class<?> operation = Class.forName("com.example.interfacedemo.designMode.mode3.factory.factoryImpl." + operate + "Factory");
        IFactory factory = (IFactory)operation.getDeclaredConstructor().newInstance();
        //通过工厂获得公司开发人员
        IDeveloper developer = factory.getDeveloper(operate);
        result += developer.work() +"\n";
        result += developer.skill() +"\n";
        //通过工厂获得公司产品人员
        IProductor productor = factory.getProductor(operate);
        result += productor.work() +"\n";
        result += productor.skill() +"\n";
        System.out.println(result);

    }
}
