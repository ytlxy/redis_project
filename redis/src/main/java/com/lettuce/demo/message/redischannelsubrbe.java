package com.lettuce.demo.message;
import com.lettuce.demo.util.redisutil;
import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.reactive.ChannelMessage;
import io.lettuce.core.pubsub.api.reactive.RedisPubSubReactiveCommands;
import reactor.core.Disposable;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class redischannelsubrbe {
    public static final String CHANNEL_NAME="message-channel";

    public static void main(String[] args)throws Exception {
        RedisClient redisClient=redisutil.getRedisClient();
        StatefulRedisPubSubConnection<String,String> connection=redisClient.connectPubSub();
        RedisPubSubReactiveCommands<String, String> reactive=connection.reactive();
        reactive.subscribe(CHANNEL_NAME).block();
        Disposable disposable =reactive.observeChannels().doOnNext(new Consumer<ChannelMessage<String, String>>() {
            @Override
            public void accept(ChannelMessage<String, String> stringStringChannelMessage) {
                System.out.println(String.format("接受信息内容为",stringStringChannelMessage.getMessage()));
            }
        }).subscribe();
        System.out.println("_______________等待消息接收_______________");
        TimeUnit.DAYS.sleep(Long.MAX_VALUE);
        disposable.dispose();
        connection.close();
    }
}
