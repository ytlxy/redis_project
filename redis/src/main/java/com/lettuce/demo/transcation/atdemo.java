package com.lettuce.demo.transcation;
import com.lettuce.demo.util.redisutil;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.TransactionResult;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;


public class atdemo {
    public static void main(String[] args)throws Exception {
        StatefulRedisConnection connection = redisutil.getConnection();
        RedisAsyncCommands commands=connection.async();
        RedisFuture mutli=commands.multi();
        RedisFuture setcmda=commands.set("msg01","jingji");
        RedisFuture setcmdb=commands.set("msg02","zhangzijie");
        RedisFuture setcmdc=commands.set("msg03","feng");
        RedisFuture setcmdd=commands.set("msg04","zhaiyiqian");
        RedisFuture<TransactionResult> exec=commands.exec();
        System.out.println("开启事务"+mutli.get());
        System.out.println("设置数据"+setcmda.get()+setcmdb.get()+setcmdc.get()+setcmdd.get());
        System.out.println("事务提交"+exec.get());
        redisutil.close();
    }
}
