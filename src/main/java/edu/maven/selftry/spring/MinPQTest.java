package edu.maven.selftry.spring;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MinPQTest {

    @Test
    // original method
    public void testSimpleExample() {
        // 手动设置属性和添加
        MinPQHeap<String> items = new MinPQHeap<>();
        items.setOwner("嘉穗");
        items.add("秦老幺", 1);
        items.add("秦老二", 2);
        items.add("秦老三", 3);
        items.add("秦老大", 4);

        items.printOwner();
        items.printArray();
    }

    @Test
    // Spring实现
    public void testSpring() {
        // 创建工厂
        ApplicationContext appCon = new ClassPathXmlApplicationContext("applicationContext.xml");

        // 通过工厂获得类
        MinPQInterface<String> items = (MinPQInterface) appCon.getBean("秦MinPQHeap");

        System.out.println(ApplicationContextHolder.validContext());
        // 之后获取此context的bean时则可用：
//        if(ApplicationContextHolder.validContext()){
//            MinPQInterface<String> items = ApplicationContextHolder.getBean("其他MinPQHeap");
//        }


//        items.add("秦老大", 4);
//        items.printOwner();
//        System.out.println("Owner: " + items.getOwner());
//        items.printArray();
    }
}
