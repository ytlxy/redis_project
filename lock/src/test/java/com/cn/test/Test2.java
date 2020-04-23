package com.cn.test;

public class Test2 implements Runnable{
    private static volatile Integer times=0;

    public static void doTask(){
        while (times<=20){
            System.out.println(times++ +":id"+Thread.currentThread().getId());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void run() {
        Test2.doTask();
    }
}
