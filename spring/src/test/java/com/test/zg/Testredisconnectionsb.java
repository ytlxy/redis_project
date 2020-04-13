package com.test.zg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.TimeUnit;

@ContextConfiguration(locations = {"classpath:spring/*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class Testredisconnectionsb {
    public static final String CHANNEL_NAME = "CCTV-5";
    @Autowired
    private RedisConnectionFactory connectionFactory;
    @Test
    public void testreceive()throws Exception{
        RedisConnection connection= this.connectionFactory.getConnection();
        RedisSerializer<String> stringSerializer=RedisSerializer.string();
        byte[] channel = stringSerializer.serialize(CHANNEL_NAME);
        connection.subscribe(new MessageListener() {
            @Override
            public void onMessage(Message message, byte[] bytes) {
                System.out.println(String.format("%s[注意]接收到消息：%s",new String(message.getChannel()),new String(message.getBody())));
            }
        },channel);
        TimeUnit.DAYS.sleep(Long.MAX_VALUE);
    }
}
