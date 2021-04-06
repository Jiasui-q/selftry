package edu.maven.selftry.spring;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Aspect
public class MyAspect {

    @Pointcut("execution(* print*())")
    public void allPrintMethods(){}

    @Pointcut("within(edu.maven.selftry.spring.MinPQHeap)")
    public void allHeapMethods(){}

    @Around("allPrintMethods() && allHeapMethods()")
    public void aroundPrint(ProceedingJoinPoint proceedingJoinPoint){
        try{
            System.out.println("Print method is called in MinPQHeap.");
            proceedingJoinPoint.proceed();
            System.out.println("Print method is complete.");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Before("within(edu.maven.selftry.spring.*)")
    public void className(JoinPoint joinPoint){
        System.out.println("我来自Class: " + joinPoint.getTarget());
    }

    @AfterReturning("args(item, priority)")
    public void itemsInfo(Object item, double priority){
        System.out.println("(" + item.toString() + ", " + priority + ") is passed.");
    }

    public void printOwner(String name){
        System.out.println(name + "'s min heap.");
    }
}
