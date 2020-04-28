package com.cn.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestCountDownLatch {
    private static final int TASKS=10;

    public static void main(String[] args) {
        System.out.println("开始");
        ExecutorService service= Executors.newFixedThreadPool(5);
        CountDownLatch downLatch=new CountDownLatch(TASKS);
        try {
            for(int x=0;x<TASKS;x++){
                service.execute(new MyWorks(downLatch,x));
            }
            System.out.println("await");
            downLatch.await();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            service.shutdown();
            System.out.println("结束");
        }
    }
}
