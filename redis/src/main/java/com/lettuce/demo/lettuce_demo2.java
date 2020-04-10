package com.lettuce.demo;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;

public class lettuce_demo2 {
    public static final String PEDIS_HOST="redis";
    public static final int REDIS_PORT=6379;
    public static final String REDIS_AUTH="redis";
    public static final int REDIS_DATABASS_INDEX=0;

    public static void main(String[] args) {
        RedisURI aredisuri=RedisURI.Builder.redis(PEDIS_HOST).withPort(REDIS_PORT).withPassword(REDIS_AUTH).withDatabase(REDIS_DATABASS_INDEX).build();
        RedisClient redisClient = RedisClient.create(aredisuri);
        StatefulRedisConnection<String,String> connect = redisClient.connect();
        System.out.println("连接:"+connect);
        connect.close();
        redisClient.shutdown();
    }
}
