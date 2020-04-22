package com.lettuce.demo.message;

import com.lettuce.demo.util.redisutil;
import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.reactive.RedisPubSubReactiveCommands;

public class redischannel {
    public static final String CHANNEL_NAME="message-channel";

    public static void main(String[] args){
        RedisClient redisClient=redisutil.getRedisClient();
        StatefulRedisPubSubConnection<String,String> connection=redisClient.connectPubSub();
        RedisPubSubReactiveCommands<String,String> reactive=connection.reactive();
        reactive.publish(CHANNEL_NAME,"xian lixun keji xuexuan").subscribe();
        connection.close();
    }
}


