package com.example.interfacedemo.designMode.impl;

import com.example.interfacedemo.designMode.inter.MathOperation;

public class DivideOperation implements MathOperation {
    @Override
    public double apply(int a, int b) {
        return (double) a / b;
    }
}

