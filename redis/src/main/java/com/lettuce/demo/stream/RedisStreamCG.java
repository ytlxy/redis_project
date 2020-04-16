package com.lettuce.demo.stream;
import com.lettuce.demo.util.redisutil;
import io.lettuce.core.XReadArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.util.Collections;

public class RedisStreamCG {
    public static final String STREAM_ID="message-01";
    public static final String GROUP_NAME="Hellogp";

    public static void main(String[] args) {
        StatefulRedisConnection connection=redisutil.getConnection();
        RedisCommands commands=connection.sync();
        commands.xadd(STREAM_ID, Collections.singletonMap("init","value"));
        XReadArgs.StreamOffset<String> offset= XReadArgs.StreamOffset.from(STREAM_ID,"$");
        commands.xgroupCreate(offset,GROUP_NAME);
        redisutil.close();
    }
}

