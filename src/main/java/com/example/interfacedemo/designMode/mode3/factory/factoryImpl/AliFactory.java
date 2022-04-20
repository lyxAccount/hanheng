package com.example.interfacedemo.designMode.mode3.factory.factoryImpl;

import com.example.interfacedemo.designMode.mode3.factory.IFactory;
import com.example.interfacedemo.designMode.mode3.inter.IDeveloper;
import com.example.interfacedemo.designMode.mode3.inter.IProductor;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class AliFactory implements IFactory {
    @Override
    public IProductor getProductor(String type) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //因为类名首字母大写，所以转换操作类型为类名格式
        String operate = type.substring(0,1).toUpperCase() + type.substring(1).toLowerCase();
        Class<?> operation = Class.forName("com.example.interfacedemo.designMode.mode3.impl." + operate + "Productor");
        return Optional.of((IProductor)operation.getDeclaredConstructor().newInstance()).orElseThrow(() ->
                new IllegalArgumentException("未知的公司"));
    }

    @Override
    public IDeveloper getDeveloper(String type) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //因为类名首字母大写，所以转换操作类型为类名格式
        String operate = type.substring(0,1).toUpperCase() + type.substring(1).toLowerCase();
        Class<?> operation = Class.forName("com.example.interfacedemo.designMode.mode3.impl." + operate + "Developer");
        return Optional.of((IDeveloper)operation.getDeclaredConstructor().newInstance()).orElseThrow(() ->
                new IllegalArgumentException("未知的公司"));
    }
}
