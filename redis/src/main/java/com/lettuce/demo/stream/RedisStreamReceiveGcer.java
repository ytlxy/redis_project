package com.lettuce.demo.stream;
import com.lettuce.demo.util.redisutil;
import io.lettuce.core.Consumer;
import io.lettuce.core.StreamMessage;
import io.lettuce.core.XReadArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

import java.time.Duration;
import java.util.List;

public class RedisStreamReceiveGcer {
    public static final String ID="message-01";
    public static final String GE="Hp";

    public static void main(String[] args) {
        StatefulRedisConnection connection=redisutil.getConnection();
        RedisCommands commands=connection.sync();
        XReadArgs block=XReadArgs.Builder.block(Duration.ZERO);
        XReadArgs.StreamOffset<String>offset= XReadArgs.StreamOffset.from(ID,">");
        for (int i=0;i<3;i++){
            int temp=i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<StreamMessage<String,String>> list=commands.xreadgroup
                            (Consumer.from(GE,"C-"+temp),block,offset);
                    for(StreamMessage message:list){
                        System.out.println("stream"+temp+"id:"+message.getId()+"内容"+message.getBody());
                        commands.xack(ID,GE,message.getId());
                    }

                }
            }).start();
        }
        redisutil.close();
    }
}
