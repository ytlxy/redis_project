package com.lettuce.demo.data;

import com.lettuce.demo.util.redisutil;
import io.lettuce.core.api.async.RedisAsyncCommands;

public class listcm {
    public static void main(String[] args)throws Exception {
        RedisAsyncCommands commands= redisutil.getConnection().async();
        commands.flushall().get();
        commands.lpush("message","hello-a","hello-b","hello-c").get();
        System.out.println("List队列内容"+commands.lrange("message",0,-1).get());
        System.out.println("list队列长度"+commands.llen("message").get());
        long a =(Long) commands.llen("message").get();
        for(int x=0;x < a;x++){
            System.out.println("数据弹出"+commands.rpop("message").get());
        }
        redisutil.close();
    }


}
