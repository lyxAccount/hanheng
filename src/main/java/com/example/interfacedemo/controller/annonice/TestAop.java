package com.example.interfacedemo.controller.annonice;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TestAop {
    /**
     * 操作解释
     * @return
     */
    String  opreation();
}
