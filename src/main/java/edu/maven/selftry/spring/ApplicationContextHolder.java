package edu.maven.selftry.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class ApplicationContextHolder implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("我已经注入啦");
        context = applicationContext;
    }

    public static <T> T getBean(String id){
        if(context==null){
            return null;
        }
        return (T) context.getBean(id);
    }

    public static ApplicationContext getApplicationContext(){
        return context;
    }

    public static boolean containsBean(String id){
        if(context==null){
            return false;
        }
        return context.containsBean(id);
    }

    public static boolean validContext(){
        if(context==null){
            return false;
        }
        return true;
    }
}

