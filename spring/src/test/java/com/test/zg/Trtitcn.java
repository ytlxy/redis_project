package com.test.zg;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/*.xml"})
public class Trtitcn {
    @Autowired
    private RedisTemplate<String,String> stringRedisTemplate;
        @Test
        public void test(){
        this.stringRedisTemplate.setEnableTransactionSupport(true);
        this.stringRedisTemplate.multi();
        this.stringRedisTemplate.opsForValue().set("msg","liuyang");
            System.out.println("执行事务"+this.stringRedisTemplate.exec());
    }
}
