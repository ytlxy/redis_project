package com.test.zg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@ContextConfiguration(locations = {"classpath:spring/*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestRedistoic {
    @Autowired
    private RedisTemplate<String,String> stringRedisTemplate;
    @Test
    public void test1(){
        this.stringRedisTemplate.setEnableTransactionSupport(true);
        this.stringRedisTemplate.watch("msg");
        this.stringRedisTemplate.multi();
        this.stringRedisTemplate.opsForValue().set("msg","不断学习使自己进步");
        System.out.println("执行事务"+this.stringRedisTemplate.exec());
    }
}
