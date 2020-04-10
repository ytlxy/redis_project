package com.test.zg;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@ContextConfiguration(locations = {"classpath:spring/*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestRedisTemplateBase {
    @Autowired
    private RedisTemplate<String,String> stringRedisTemplate;
    @Test
    public void testString(){
        for(int x=0;x<10;x++){
            this.stringRedisTemplate.opsForValue().set("信息="+x,"hello-"+x);
        }
    }
    @Test
    public void testhash(){
        Map<String,String> map=new HashMap<String, String>();
        map.put("name","lao");
        map.put("hight",String.valueOf(181));
        map.put("empon",String.valueOf(16.7));
        this.stringRedisTemplate.opsForHash().putAll("laoda",map);
        System.out.println(this.stringRedisTemplate.opsForHash().get("laoda","name"));
        System.out.println(this.stringRedisTemplate.opsForHash().get("laoda","hight"));
    }
    @Test
    public void testkeys(){
        Set<String> keys=this.stringRedisTemplate.keys("msg - *");
        System.out.println("所有的key:"+keys);
    }
}
