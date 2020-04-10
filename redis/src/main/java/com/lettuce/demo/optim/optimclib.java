package com.lettuce.demo.optim;
import com.lettuce.demo.util.redisutil;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class optimclib {
    public static void main(String[] args) {
        StatefulRedisConnection connection=redisutil.getConnection();
        RedisCommands commands=connection.sync();
        commands.set("msg","helloall");
        redisutil.close();
    }
}
