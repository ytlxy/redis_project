package com.cn.redis.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Component
public class RedisLock {
    @Autowired
    private RedisTemplate<String,String> StringRedisTemplate;//取得redis模板

    public boolean lock(String serviceKey, String userid, Long timeout, TimeUnit timeUnit){
        if (this.StringRedisTemplate.opsForValue().setIfAbsent(serviceKey,userid,timeout,timeUnit)){//填入内容
            return true;
        }
        String executor=this.StringRedisTemplate.opsForValue().get(serviceKey);//取得servicekey
        if(StringUtils.isEmpty(executor)){//判断executor是否为空
            String oldValue=this.StringRedisTemplate.opsForValue().get(serviceKey);
            if (StringUtils.isEmpty(oldValue)&& oldValue.equals(userid)){//判断oldValue是否为空并且判断oldValue是否等于userid
                this.StringRedisTemplate.opsForValue().setIfAbsent(serviceKey,userid,timeout,timeUnit);
                return true;
            }
        }
        return false;
    }
    public void unlock(String serviceKey,String userid){//开锁操作
        String oldValue=this.StringRedisTemplate.opsForValue().get(serviceKey);//取得servicekey
        if(!StringUtils.isEmpty(oldValue)&& oldValue.equals(userid)){//判断oldValue是否为不为空并且判断oldValue是否等于userid
            this.StringRedisTemplate.opsForValue().getOperations().delete(serviceKey);//删除serviceKey
        }
    }
}
