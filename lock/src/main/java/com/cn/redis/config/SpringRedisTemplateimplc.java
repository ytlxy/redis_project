package com.cn.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class SpringRedisTemplateimplc {
    private static final String STNX="NX";
    private static final String SET_EXPIER_TIME="PX";
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public SpringRedisTemplateimplc(RedisTemplate redisTemplate){
        this.redisTemplate=redisTemplate;
        this.stringRedisTemplate=new StringRedisTemplate();

        this.stringRedisTemplate.setConnectionFactory(redisTemplate.getConnectionFactory());
        this.stringRedisTemplate.afterPropertiesSet();
    }
    public boolean addRedisLock(String lockkey,String requesId,long expireTime){
        boolean result=stringRedisTemplate.opsForValue().setIfAbsent(lockkey,requesId,expireTime, TimeUnit.SECONDS);
        return result;
    }
}
