package com.lettuce.demo.optim;
import com.lettuce.demo.util.redisutil;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.concurrent.TimeUnit;

public class optimclia {
    public static void main(String[] args)throws Exception {
        StatefulRedisConnection connection=redisutil.getConnection();
        RedisCommands commands=connection.sync();
        commands.watch("msg");
        commands.multi();
        commands.set("msg","zhangsan");
        TimeUnit.SECONDS.sleep(10);
        System.out.println("事务影响行数"+commands.exec().size());
        redisutil.close();
    }

}
