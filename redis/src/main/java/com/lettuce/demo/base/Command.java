package com.lettuce.demo.base;

import com.lettuce.demo.util.redisutil;
import io.lettuce.core.api.sync.RedisCommands;

public class Command {
    public static void main(String[] args) {
        RedisCommands command= redisutil.getConnection().sync();
        command.set("liao","hello");
        System.out.println("获取指定数据"+command.get("liao"));
        redisutil.close();
    }
}
