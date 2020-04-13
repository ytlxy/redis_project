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
public class TestRedisTemplatepb {
    public static final String CHANNEL_NAME = "CCTV-5";
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void test()throws Exception{
        for(int x=0;x<100;x++){
            TimeUnit.SECONDS.sleep(1);
        this.redisTemplate.convertAndSend(CHANNEL_NAME,"hello,比赛赢了"+x);
        }
    }
}
