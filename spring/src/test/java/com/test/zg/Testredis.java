package com.test.zg;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations={"classpath:spring/*.xml"})


public class Testredis {
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Test
    public void testRedis(){
    System.out.println(this.redisConnectionFactory);
    }
}
