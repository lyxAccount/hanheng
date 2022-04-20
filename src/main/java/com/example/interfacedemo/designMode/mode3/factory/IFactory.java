package com.example.interfacedemo.designMode.mode3.factory;

import com.example.interfacedemo.designMode.mode3.inter.IDeveloper;
import com.example.interfacedemo.designMode.mode3.inter.IProductor;

import java.lang.reflect.InvocationTargetException;

public interface IFactory {
    IProductor getProductor(String type) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException;

    IDeveloper getDeveloper(String type) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, InstantiationException;
}
