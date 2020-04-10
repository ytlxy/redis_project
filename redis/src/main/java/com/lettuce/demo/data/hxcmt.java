package com.lettuce.demo.data;
import com.lettuce.demo.util.redisutil;
import io.lettuce.core.api.async.RedisAsyncCommands;

import java.util.HashMap;
import java.util.Map;

public class hxcmt {
    public static void main(String[] args)throws Exception {
        RedisAsyncCommands commands=redisutil.getConnection().async();
        commands.hset("lao","name","阿三").get();
        Map<String,String> map=new HashMap<String, String>();
        map.put("hig",String.valueOf(181));
        map.put("emp",String.valueOf(6));
        commands.hmset("lao",map).get();
        System.out.println("获取hash数据"+commands.hgetall("lao").get());
        redisutil.close();
    }
}
