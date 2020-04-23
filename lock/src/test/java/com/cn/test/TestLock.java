package com.cn.test;

import com.cn.redis.util.redisLockutli;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@ContextConfiguration(locations = {"classpath:spring/*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestLock {
    @Autowired
    private redisLockutli redisLockutli;
    @Test
    public void coutestLoads() throws InterruptedException {
        ThreadPoolExecutor executor=new ThreadPoolExecutor(3,3,50,
                TimeUnit.SECONDS,new LinkedBlockingDeque<>(10));
        executor.execute(new Test2());
        executor.execute(new Test2());
        executor.execute(new Test2());
        while (true){
            Thread.sleep(1000);
        }
    }
}
