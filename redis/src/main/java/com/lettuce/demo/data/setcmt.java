package com.lettuce.demo.data;

import com.lettuce.demo.util.redisutil;
import io.lettuce.core.api.async.RedisAsyncCommands;

public class setcmt {
    public static void main(String[] args)throws Exception {
        RedisAsyncCommands commands= redisutil.getConnection().async();
        commands.flushdb().get();
        commands.sadd("student-feng","a","b","c","d","x","y","z");
        commands.sadd("student-gong","a","b","c","e","f","g","h");
        System.out.println("交集运算"+commands.sinter("student-feng","student-gong").get());
        System.out.println("差集运算"+commands.sdiff("student-feng","student-gong").get());
        System.out.println("并集运算"+commands.sunion("student-feng","student-gong").get());
        redisutil.close();
    }
}
