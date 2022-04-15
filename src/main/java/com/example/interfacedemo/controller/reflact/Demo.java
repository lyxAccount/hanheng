package com.example.interfacedemo.controller.reflact;

import java.lang.reflect.Method;

public class Demo {
    public static void main(String[] args) throws Exception {
        test("da");
    }

    public static void test(String str) throws Exception{
        //        Class<Student> studentClass = Student.class;
//        Student student = studentClass.newInstance();
//        Method setName = studentClass.getMethod("setName", String.class);
//        Method getName = studentClass.getMethod("getName", new Class[]{});
//        setName.invoke(student,"test");
//        System.out.println(getName.invoke(student));


//        Student student = new Student();
//        Class studentClass = student.getClass();
//        Method setName = studentClass.getMethod("setName", String.class);
//        Method getName = studentClass.getMethod("getName", new Class[]{});
//        setName.invoke(student,"cc");
//        System.out.println(getName.invoke(student));
//        Constructor declaredConstructor = studentClass.getConstructor();
//        Student student1 = (Student)declaredConstructor.newInstance();
//        student1.setName("aaa");
//        System.out.println(student1.getName());


        Class aClass = Class.forName("com.example.interfacedemo.controller.pojo.Student");
//        Method[] methods = aClass.getMethods();
//        for(Method method : methods){
//            System.out.println(method.getName());
//        }
        Method getMethod = aClass.getMethod("getName", new Class[]{});
        Method setMethod = aClass.getMethod("setName", String.class);
        Object o = aClass.newInstance();
        setMethod.invoke(o,"ddd");
        System.out.println(getMethod.invoke(o));
    }
}
