package com.cn.Test;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class MyWorks implements Runnable{
    private CountDownLatch countDownLatch;
    private int Countest;
    private static final Random rand=new Random(951413);

    public MyWorks(CountDownLatch downLatch ,int countest){
        this.countDownLatch=downLatch;
        this.Countest=countest;
    }
    @Override
    public void run() {
        doTask();
        countDownLatch.countDown();
    }
    public void doTask(){
        String name = Thread.currentThread().getName();
        System.out.println(name +"MyWorks:BEGIN:contest="+Countest);
        try {
            Thread.sleep(rand.nextInt(3000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(name+"MyWorks:END:contest="+Countest);
        }
    }
}
