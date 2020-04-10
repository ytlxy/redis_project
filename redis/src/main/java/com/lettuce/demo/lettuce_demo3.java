package com.lettuce.demo;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;

public class lettuce_demo3 {
    public static final String REDIS_ADDRESS="redis://redis@redis/0";

    public static void main(String[] args) {
        RedisURI redisURI=RedisURI.create(REDIS_ADDRESS);
        RedisClient redisClient=RedisClient.create(redisURI);
        StatefulRedisConnection<String,String> connect=redisClient.connect();
        System.out.println("连接"+connect);
        connect.close();
        redisClient.shutdown();
    }
}
