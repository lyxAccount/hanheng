package com.example.interfacedemo.designMode.singleton;

/**
 * @author liyouxiang 2023/11/9
 *
 * des:
 * 双重检查锁   第一个if判断,第一个线程进来new了Singleton，那么singleton就有值，第二个线程进来，
 * 那么进行第一个if判断，不为null，直接返回，不用再去new了，提升了效率
 *
 * 第二个if判断,两个线程同时进来，在synchronized，第一个线程进入，另一个线程等待，第一个线程new Singleton，
 * 然后返回，另一个线程发现了第一个线程走了，进入synchronized，如果不进行if判断，那么还会new Singleton，导致线程不安全
 */
public class Singleton {

    private Singleton() {
        System.out.println("创建对象时执行");
    }

    public static Singleton singleton = null;

    public static synchronized Singleton getInstance(){
        if(singleton == null){
            synchronized (Singleton.class){
                if(singleton == null){
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}
