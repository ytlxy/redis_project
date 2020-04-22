package com.cn.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@ContextConfiguration(locations = {"classpath:spring/*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TicketTest {
    private int count=100;
    private Lock lock=new ReentrantLock();
    @Test
    public void test()throws InterruptedException{
        TicketRunnable tr=new TicketRunnable();
        Thread t1= new Thread(tr,"窗口1");
        Thread t2=new Thread(tr,"窗口2");
        Thread t3=new Thread(tr,"窗口3");
        Thread t4=new Thread(tr,"窗口4");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        Thread.currentThread().join();
    }
    public class TicketRunnable implements Runnable{
        @Override
        public void run() {
            while (count>0){
                lock.lock();
                if(count>0){
                    System.out.println(Thread.currentThread().getName()+"售出第"+(count--)+"张票");
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    lock.unlock();
                }
            }
        }
    }
}
