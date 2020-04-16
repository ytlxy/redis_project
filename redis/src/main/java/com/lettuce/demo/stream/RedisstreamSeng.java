package com.lettuce.demo.stream;

import com.lettuce.demo.util.redisutil;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RedisstreamSeng {
    private static final String STREAM_ID="message-01";

    public static void main(String[] args) {
        StatefulRedisConnection connection= redisutil.getConnection();
        RedisCommands commands=connection.sync();
            Map<String,String> bodymap=new HashMap<String, String>();
            bodymap.put("name","zhangsan");
            bodymap.put("empno","7");
            System.out.println("信息发送id:"+commands.xadd(STREAM_ID,bodymap));
    }
}
