package com.lettuce.demo.data;
import com.lettuce.demo.util.redisutil;
import io.lettuce.core.GeoArgs;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.api.async.RedisAsyncCommands;

public class geocmd {
    public static void main(String[] args)throws Exception {
        RedisAsyncCommands commands=redisutil.getConnection().async();
        commands.flushdb().get();
        commands.geoadd("point",120.182458,30.477244,"运河码头");
        commands.geoadd("point",120.184732,30.479445,"1t街");
        commands.geoadd("point",120.183413,30.478026,"gjq");
        RedisFuture future=commands.georadius("point",120.184233,30.478996,2000, GeoArgs.Unit.m);
        System.out.println("周围标志物"+future.get());
        redisutil.close();
    }
}
