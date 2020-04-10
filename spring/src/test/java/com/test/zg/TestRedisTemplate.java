package com.test.zg;

import com.zg.redis.config.Member;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath:spring/*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestRedisTemplate {
   @Autowired
//    private RedisTemplate<String,String> redisTemplate;
    private RedisTemplate<String,Object> redisTemplate;
   @Test
   public void testSet(){
//       Member member=new Member();
//       member.setId(292932932923L);
//       member.setAge(84);
//       member.setName("大王八");
       this.redisTemplate.opsForList().rightPushAll("member-list","a","b","c","d","f","g","sd","h","sd","sd","sg","sg","s",2,54,2,6,234,6346,6);
       System.out.println(redisTemplate.opsForList().range("member-list",0,-1));
       redisTemplate.delete("member-list");
   }
   @Test
    public void testString(){
        this.redisTemplate.execute(new RedisCallback<Object>(){

            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.flushDb();
                return "OK";
            }
        });
        for(int x=0;x<10;x++){
            this.redisTemplate.opsForValue().set("message-"+x,"hello-"+x);
        }
    }
    @Test
    public void testGet(){
       System.out.println("查询数据"+this.redisTemplate.opsForValue().get("member-wb"));
//        System.out.println("获取数据"+this.redisTemplate.opsForValue().get("message-3"));
    }
}
