package com.cn.redis.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class springutli implements ApplicationContextAware{
    private  static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(springutli.applicationContext== null){
            springutli.applicationContext = applicationContext;
        }
        System.out.println("配置成功="+springutli.applicationContext);
    }
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }
    public static<T> T getBean(Class<T> clazz){
        return  getApplicationContext().getBean(clazz);
    }
    public static<T> T getBean(String name, Class<T> clazz){
        return getApplicationContext().getBean(name,clazz);
    }
}
