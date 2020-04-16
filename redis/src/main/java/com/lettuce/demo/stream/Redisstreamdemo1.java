package com.lettuce.demo.stream;

import com.lettuce.demo.util.redisutil;
import io.lettuce.core.RedisFuture;
import io.lettuce.core.StreamMessage;
import io.lettuce.core.XReadArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;

import java.time.Duration;
import java.util.List;

public class Redisstreamdemo1 {
    private static final String STREAM_ID="message-01";

    public static void main(String[] args)throws Exception {
        StatefulRedisConnection connection= redisutil.getConnection();
        RedisAsyncCommands commands=connection.async();
        XReadArgs block=XReadArgs.Builder.block(Duration.ZERO);
        XReadArgs.StreamOffset<String> offset=XReadArgs.StreamOffset.from(STREAM_ID,"$");
        boolean flay=true;
        while (flay){
            RedisFuture<List<StreamMessage<String,String>>> future=commands.xread(block,offset);
            List<StreamMessage<String,String>>managers= future.get();
            for(StreamMessage message : managers){
                System.out.println("[ConsumerA]接收消息id："+message.getId()+"内存："+message.getBody());
            }
        }
        redisutil.close();
    }
}
