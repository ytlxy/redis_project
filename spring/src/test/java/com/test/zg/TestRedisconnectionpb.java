package com.test.zg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@ContextConfiguration(locations = {"classpath:spring/*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestRedisconnectionpb {
    public static final String CHANNEL_NAME = "CCTV-5";
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Test
    public void test()throws Exception{
        RedisConnection connection=this.redisConnectionFactory.getConnection();
        RedisSerializer<String> stringSerializer= RedisSerializer.string();
        byte[] channer=stringSerializer.serialize(CHANNEL_NAME);
        for(int a=0;a<100;a++){
            TimeUnit.SECONDS.sleep(1);
            connection.publish(channer,("hello,重要信息,比赛赢了"+a).getBytes());
        }
    }
}
