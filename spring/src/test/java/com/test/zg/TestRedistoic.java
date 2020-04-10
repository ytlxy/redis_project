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
        this.stringRedisTemplate.watch("星智是好学生");
        this.stringRedisTemplate.multi();
        this.stringRedisTemplate.opsForValue().set("星智是好学生","分数是76");
        System.out.println("执行事务"+this.stringRedisTemplate.exec());
    }
}
