package com.lettuce.demo.stream;
import com.lettuce.demo.util.redisutil;
import io.lettuce.core.ScanArgs;
import io.lettuce.core.ScanStream;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import reactor.core.publisher.Flux;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class RedisSreamscan {
    public static final String STREAM_ID="message-01";

    public static void main(String[] args) throws Exception{
        StatefulRedisConnection connection=redisutil.getConnection();
        RedisReactiveCommands reactive=connection.reactive();
        Flux flux=ScanStream.scan(reactive, ScanArgs.Builder.limit(100));
        while (true){
            flux.doOnNext(new Consumer() {
                @Override
                public void accept(Object o) {
                    System.out.println("扫描key:"+o);
                }
            }).subscribe();
            TimeUnit.DAYS.sleep(Long.MAX_VALUE);
        }
    }
}
