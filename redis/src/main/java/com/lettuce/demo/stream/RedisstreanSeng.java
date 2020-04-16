package com.lettuce.demo.stream;

import com.lettuce.demo.util.redisutil;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RedisstreanSeng {
    private static final String STREAM_ID="message-01";

    public static void main(String[] args)throws Exception {
        StatefulRedisConnection connection=redisutil.getConnection();
        RedisCommands commands=connection.sync();
        for(int x=0;x<10;x++){
            TimeUnit.SECONDS.sleep(1);
            Map<String,String> bodymap=new HashMap<String, String>();
            bodymap.put("head","lilongtianyimessage-z1"+x);
            bodymap.put("data","hello-"+x);
            bodymap.put("sign","hello2"+x);
            System.out.println("信息发送id:"+commands.xadd(STREAM_ID,bodymap));
        }
    }
}
