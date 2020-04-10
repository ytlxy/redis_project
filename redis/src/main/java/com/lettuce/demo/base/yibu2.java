package com.lettuce.demo.base;

import com.lettuce.demo.util.redisutil;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.async.RedisAsyncCommands;

public class yibu2 {
    public static void main(String[] args) throws Exception{
        RedisAsyncCommands commands = redisutil.getConnection().async();
        RedisFuture future=commands.keys("*");
        System.out.println("所有key"+future.get());
        redisutil.close();
    }
}
