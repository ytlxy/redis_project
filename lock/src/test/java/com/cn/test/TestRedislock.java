package com.cn.test;

import com.cn.redis.util.RedisLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@ContextConfiguration(locations = {"classpath:spring/*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestRedislock {
    public static final String SERVICE_KET="money";
@Autowired
    private RedisLock redisLock;
    @Test
    public void Test()throws Exception{
        for(int a=0;a<20;a++){
            TimeUnit.SECONDS.sleep(2);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("{"+Thread.currentThread()
                            .getName()+"}准备执行"+SERVICE_KET+"操作业务");
                    if(redisLock.lock(SERVICE_KET,Thread.currentThread().getName(),11L,TimeUnit.SECONDS)){
                        System.err.println("["+Thread.currentThread().getName()+"]获取锁，执行"+SERVICE_KET+"业务!");
                        try {
                            TimeUnit.SECONDS.sleep(3);
                        } catch (InterruptedException e) {}
                        redisLock.unlock(SERVICE_KET,Thread.currentThread().getName());
                    }else{
                        System.out.println("["+Thread.currentThread().getName()+"]未获得锁");
                    }
                }
            },"userid-"+a).start();
        }
        TimeUnit.DAYS.sleep(Long.MAX_VALUE);
    }
}
