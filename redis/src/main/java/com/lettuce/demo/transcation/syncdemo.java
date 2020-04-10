package com.lettuce.demo.transcation;
import com.lettuce.demo.util.redisutil;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.concurrent.TimeUnit;

public class syncdemo {
    public static void main(String[] args) {
        StatefulRedisConnection connection=redisutil.getConnection();
        RedisCommands commands = connection.sync();
        System.out.println("开启事务"+commands.multi());
        System.out.println("设置"+commands.set("星智是好学生","分数是76"));
        System.out.println("提交"+commands.exec());
        redisutil.close();
    }
}
