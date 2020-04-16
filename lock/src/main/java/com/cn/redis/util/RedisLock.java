package com.cn.redis.util;

import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import sun.misc.Contended;

import java.util.concurrent.TimeUnit;

@Contended

public class RedisLock {
    @Autowired
    private RedisTemplate<String,String> StringRedisTemplate;

    public boolean lock(String serviceKey, String userid, Long timeout, TimeUnit timeUnit){
        if (this.StringRedisTemplate.opsForValue().setIfAbsent(serviceKey,userid,timeout,timeUnit)){
            return true;
        }
        String executor=this.StringRedisTemplate.opsForValue().get(serviceKey);
        if(StringUtils.isEmpty(executor)){
            String oldValue=this.StringRedisTemplate.opsForValue().get(serviceKey);
            if (StringUtils.isEmpty(oldValue)&& oldValue.equals(userid)){
                this.StringRedisTemplate.opsForValue().setIfAbsent(serviceKey,userid,timeout,timeUnit);
                return true;
            }
        }
        return false;
    }

    public void unlock(String serviceKey,String userid){
        String oldValue=this.StringRedisTemplate.opsForValue().get(serviceKey);
        if(!StringUtils.isEmpty(oldValue)&& oldValue.equals(userid)){
            this.StringRedisTemplate.opsForValue().getOperations().delete(serviceKey);
        }
    }
}
