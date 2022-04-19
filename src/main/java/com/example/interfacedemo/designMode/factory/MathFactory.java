package com.example.interfacedemo.designMode.factory;

import com.example.interfacedemo.designMode.impl.AddOperation;
import com.example.interfacedemo.designMode.impl.DivideOperation;
import com.example.interfacedemo.designMode.impl.MultiplyOperation;
import com.example.interfacedemo.designMode.impl.SubOperation;
import com.example.interfacedemo.designMode.inter.MathOperation;

import java.util.Optional;

/**
 * 工厂类 简单工厂模式(非静态)
 */
public class MathFactory {

    /**
     * 获取具体的操作类型
     * @param operator
     * @return
     */
    public static Optional<MathOperation> getOperation(String operator) {
        MathOperation result = null;
        if("add".equals(operator)){
            result = new AddOperation();
        }else if("sub".equals(operator)){
            result = new SubOperation();
        }else if("multiply".equals(operator)){
            result = new MultiplyOperation();
        }else if("divide".equals(operator)){
            result = new DivideOperation();
        }
        return Optional.ofNullable(result);
    }
}
