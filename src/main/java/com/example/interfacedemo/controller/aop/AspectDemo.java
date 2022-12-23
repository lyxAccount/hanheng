package com.example.interfacedemo.controller.aop;

import com.example.interfacedemo.controller.annonice.TestAop;
import com.example.interfacedemo.controller.pojo.Student;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;

@Aspect
@Component
@Order(1)
public class AspectDemo {

    @Pointcut("@annotation(com.example.interfacedemo.controller.annonice.TestAop)")
    private void test(){

    }

    @Before(value ="test()")
    public void before(JoinPoint point) throws ClassNotFoundException {
        String methodName = point.getSignature().getName();
        //获取传入目标方法的参数对象
        String operateExplain = "";
        Object[] args = point.getArgs();
        Class<?> aClass = Class.forName(point.getTarget().getClass().getName());
        Method[] methods = aClass.getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (methods[i].getName() != methodName) {continue;}
            Class<?>[] clazzs = methods[i].getParameterTypes();
            if (clazzs.length == args.length) {
                operateExplain =	methods[i].getAnnotation(TestAop.class).opreation();
                break;
            }

        }
        System.out.println("在指定注解的方法执行前执行");
        System.out.println("获取的操作为--"+operateExplain);
    }

    @After(value = "test()")
    public void after(){
        System.out.println("在指定注解的方法执行后执行");
    }

    @AfterReturning(value ="test()",returning = "list")
    public void afterReturning(List<Student> list){
        System.out.println("学生的数量有"+list.size()+"个");
    }

    @Around("test()")
    public void around(ProceedingJoinPoint point) throws Throwable {
        System.out.println("around");
        //这样可以获取该方法的返回值
        Object result = point.proceed(point.getArgs());
        System.out.println("返回值是"+result.toString());
        //获取的是方法的入参，如果方法是无参的，获取不到值
        Object[] args = point.getArgs();
        for (Object arg : args) {
            System.out.println("获取的请求参数为"+arg.toString());
        }
        System.out.println("在around before after afterReturning注解都有的时候，最先执行around中的方法，如果around中没有调用process方法，那么只有around注解会生效" +
                "，如果调用了process方法，执行顺序为 around before afterReturning after 最后为process方法");
    }
}
