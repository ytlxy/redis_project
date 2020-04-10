package com.lettuce.demo;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;

public class lettuce_demo {
    public static final String PEDIS_HOST="redis";
    public static final int REDIS_PORT=6379;
    public static final String REDIS_AUTH="redis";
    public static final int REDIS_DATABASS_INDEX=0;

    public static void main(String[]args){
        RedisURI redis= RedisURI.create(PEDIS_HOST,REDIS_PORT);
        redis.setDatabase(REDIS_DATABASS_INDEX);
        redis.setPassword(REDIS_AUTH);
        RedisClient redisClient=RedisClient.create(redis);
        StatefulRedisConnection <String ,String> connection = redisClient.connect();
        System.out.println("连接："+connection);
        connection.close();
        redisClient.shutdown();
    }
}
//192.168.81.136