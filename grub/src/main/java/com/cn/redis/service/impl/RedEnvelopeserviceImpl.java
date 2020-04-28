package com.cn.redis.service.impl;

import com.cn.redis.service.IRedEnvelopeserice;
import com.cn.redis.util.SplitCheckUtil;
import com.cn.redis.util.SplitMoneyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RedEnvelopeserviceImpl implements IRedEnvelopeserice {
    @Autowired
    private RedisTemplate<String,Double> redisTemplate;
    @Override
    public String add(String userid, int amount, double money) {
        SplitMoneyUtil splitMoneyUtil=new SplitMoneyUtil(amount,money);
        if (!SplitCheckUtil.check(splitMoneyUtil.getAllPackages(),money)){
            return null;
        }
        String key=PREFIX+userid+"-"+System.currentTimeMillis();
        if (this.redisTemplate.opsForList().leftPushAll(key,splitMoneyUtil
                .getAllPackages()) == splitMoneyUtil.getAllPackages().size()){
            return key;
        }
        return null;
    }

    @Override
    public Double grab(String key, String userid) {
        String resultKey=key+SUFFIX;
        if (this.redisTemplate.opsForHash().hasKey(resultKey,userid)){
            return null;
        }else {
            Double grabmoney=this.redisTemplate.opsForList().leftPop(key);
            if(grabmoney != null){
                this.redisTemplate.opsForHash().put(resultKey,userid,grabmoney);
            }
            return grabmoney;
        }
    }

    @Override
    public Map<String, Double> result(String key) {
        Map<String ,Double> resultmap=new HashMap<>();
        if(this.redisTemplate.hasKey(key+SUFFIX)){
            Map<Object,Object> map=this.redisTemplate.opsForHash().entries(key+SUFFIX);
            if(map != null){
                for (Map.Entry<Object,Object> entry: map.entrySet()){
                    resultmap.put((String) entry.getKey(),(Double) entry.getValue());
                }
            }
        }
        return resultmap;
    }
}
