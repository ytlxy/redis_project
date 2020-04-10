package com.lettuce.demo.transcation;
import com.lettuce.demo.util.redisutil;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class rtadmo {
    public static void main(String[] args)throws Exception {
    StatefulRedisConnection connection= redisutil.getConnection();
    RedisReactiveCommands commands=connection.reactive();
    commands.multi().subscribe(new Consumer() {
        @Override
        public void accept(Object o) {
            System.out.println("设置数据"+commands.set("il","doog").subscribe());
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("事务提交"+commands.exec().subscribe());
        }
    });
    TimeUnit.SECONDS.sleep(20);
    }
}