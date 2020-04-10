package com.lettuce.demo.data;

import com.lettuce.demo.util.redisutil;
import io.lettuce.core.api.async.RedisAsyncCommands;

public class zsetcmt {
    public static void main(String[] args)throws Exception {
        RedisAsyncCommands commands= redisutil.getConnection().async();
        commands.flushdb().get();
        commands.zadd("fromework",91.7,"spring");
        commands.zadd("fromework",97.0,"redis");
        commands.zadd("fromework",82.0,"mybatis");
        commands.zincrby("hotass",74,"4444");
        System.out.println("升序"+commands.zrange("hotass",0,-1).get());
        System.out.println("降序"+commands.zrevrange("hotass",0,-1).get());
        System.out.println("=================================================");
        System.out.println("获取数据升序"+commands.zrangeWithScores("fromework",0,-1).get());
        System.out.println("获取数据降序"+commands.zrevrangeWithScores("fromwork",0,-1).get());
        redisutil.close();
    }
}
