package com.example.interfacedemo.designMode.mode2.impl;

import com.example.interfacedemo.designMode.impl.AddOperation;
import com.example.interfacedemo.designMode.inter.MathOperation;
import com.example.interfacedemo.designMode.mode2.inter.MathFactoryInterface;

import java.util.Optional;

public class AddFactory implements MathFactoryInterface {
    @Override
    public Optional<MathOperation> getOperation(String operator) {
        return Optional.of(new AddOperation());
    }
}
