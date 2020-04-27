package com.cn.Test;

import java.util.concurrent.CountDownLatch;

public class Work implements Runnable{
    private String name;
    private CountDownLatch latch;
    public Work(String name, CountDownLatch latch){
        this.name=name;
        this.latch=latch;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(3000);
            System.out.println(this.name+"工作完了");
            this.latch.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch=new CountDownLatch(2);
        Work emper1=new Work("zhangsan",countDownLatch);
        Work emper2=new Work("lisi",countDownLatch);
        new Thread(emper1).start();
        new Thread(emper2).start();
        countDownLatch.await();
        System.out.println("工作完成");

    }
}
