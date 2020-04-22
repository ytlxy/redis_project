package com.lettuce.demo.message;
import com.lettuce.demo.util.redisutil;
import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.reactive.RedisPubSubReactiveCommands;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class redischannlepublish {
    public static final String CHANNEL_NAME="message-channel";

    public static void main(String[] args) throws Exception{
        RedisClient redisClient=redisutil.getRedisClient();
        StatefulRedisPubSubConnection<String,String> connection=redisClient.connectPubSub();
        RedisPubSubReactiveCommands<String,String> reactive=connection.reactive();
        Disposable disposable=Flux.interval(Duration.ofSeconds(1)).subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) {
                reactive.publish(CHANNEL_NAME,"helloworld").subscribe();
            }
        });
        TimeUnit.DAYS.sleep(Long.MAX_VALUE);
        disposable.dispose();
        connection.close();
    }
}
