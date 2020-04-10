package com.lettuce.demo.base;

import com.lettuce.demo.util.redisutil;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.async.RedisAsyncCommands;

import java.util.concurrent.TimeUnit;

public class yibu {
    public static void main(String[] args)throws Exception {
        RedisAsyncCommands redisAsync = redisutil.getConnection().async();
        redisAsync.setex("msg",3,"helloworld");
        TimeUnit.SECONDS.sleep(1);
        RedisFuture redisFuture=redisAsync.get("msg");
        System.out.println("延迟1秒获取"+redisFuture);
        TimeUnit.SECONDS.sleep(3);
        System.out.println("延迟3秒消失"+redisAsync.get("msg").get());
        redisutil.close();
    }
}
