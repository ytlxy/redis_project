package com.test.zg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@ContextConfiguration(locations = {"classpath:spring/*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestRedislsxSpring {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
@Test
    public void test1() {
        List<Object> objects=this.redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                if(connection instanceof StringRedisConnection){
                    StringRedisConnection conn=(StringRedisConnection) connection;
                        conn.set("liuyang","xingzhi");
                        conn.set("dongbei","yingzhang");
                        conn.set("laogong","zijie");
                        conn.set("feng","zhai");
                        conn.set("yi","good");
                }
                return null;
            }
        });
    System.out.println(objects);
    }

}
