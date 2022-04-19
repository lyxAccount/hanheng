package com.example.interfacedemo.designMode.factory;

import com.example.interfacedemo.designMode.impl.AddOperation;
import com.example.interfacedemo.designMode.impl.DivideOperation;
import com.example.interfacedemo.designMode.impl.MultiplyOperation;
import com.example.interfacedemo.designMode.impl.SubOperation;
import com.example.interfacedemo.designMode.inter.MathOperation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 简单工厂模式-静态
 */
public class MathFactory02 {

    static Map<String, MathOperation> operationMap = new HashMap<>();
    static {
        operationMap.put("add", new AddOperation());
        operationMap.put("sub", new SubOperation());
        operationMap.put("multiply", new MultiplyOperation());
        operationMap.put("divide", new DivideOperation());
    }

    public static Optional<MathOperation> getOperation(String operator) {
        Optional<MathOperation> mathOperation = Optional.ofNullable(operationMap.get(operator));
        return mathOperation;
    }


}
