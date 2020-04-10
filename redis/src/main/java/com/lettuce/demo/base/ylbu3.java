package com.lettuce.demo.base;

import com.lettuce.demo.util.redisutil;
import io.lettuce.core.api.async.RedisAsyncCommands;

public class ylbu3 {
    public static void main(String[] args) {
        RedisAsyncCommands commands= redisutil.getConnection().async();
        commands.flushall();
        redisutil.close();
    }
}
