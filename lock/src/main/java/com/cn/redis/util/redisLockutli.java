package com.cn.redis.util;

import io.lettuce.core.SetArgs;
import io.lettuce.core.api.async.RedisAsyncCommands;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class redisLockutli {
    @Autowired
    private static RedisTemplate redisTemplate;
    private static final ThreadLocal<String> lockValue=new ThreadLocal<String>();
    private static final String keylock="lock";
    public static boolean tryLockWithtimeout(Integer timeout,Integer expireTime){
        String value=UUID.randomUUID().toString();
        long invalidTime=System.currentTimeMillis()+timeout*1000;
        boolean flag=false;
        while (System.currentTimeMillis() < invalidTime){
            flag=tryLock(keylock,value,expireTime);
            if (flag){
                break;
            }else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    System.out.println(e.toString());
                }
            }
        }
        if (false==flag){
            System.out.println("竞争锁失败");
        }
        return flag;
    }
    public static boolean blockLock(Long expireTime) throws InterruptedException {
        String value=UUID.randomUUID().toString();
        boolean flag=false;
        while (true){
            flag= tryLock(keylock,value,expireTime);
            if (flag){
                break;
            }else {
                Thread.sleep(10);
            }

        }
        if(false==flag){
            System.out.println("竞争锁失败");
        }
        return flag;
    }
    public static boolean unlock(){
        Integer retryYimes=3;
        String value=(String) redisTemplate.opsForValue().get(keylock);
        Boolean flay=false;
        String s=lockValue.get();
        if (lockValue.get().equals(value)){
            while (retryYimes>0){
                try {
                    flay=redisTemplate.delete(keylock);
                    break;
                }catch (Exception e){
                    System.out.println("释放锁异常");
                    retryYimes--;
                }

            }
        }else {

        }
        return flay;
    }
    private static boolean tryLock(String key,String value,long expireTime){
        try {
            String result=(String) redisTemplate.execute((RedisCallback) connection ->{
                try {
                    String redisResult = null;
                    Object nativeConnection = connection.getNativeConnection();
                    RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                    byte[] keyBaty = keySerializer.serialize(key);
                    byte[] valueByte = keySerializer.serialize(value);
                    if (nativeConnection instanceof RedisAsyncCommands) {
                        RedisAsyncCommands commands = (RedisAsyncCommands) nativeConnection;
                        commands.getStatefulConnection().sync().set(keyBaty, valueByte, SetArgs.Builder.nx().ex(expireTime));
                    } else {
                        System.out.println("REDISLIBMISTCH");
                    }
                    return redisResult;
                }catch (Exception e){
                    System.out.println("失败的加锁，关闭连接");
                    connection.close();
                    return "";
                }

            });
            boolean eq="OK".equals(value);
            if (eq){
                lockValue.set(value);
            }
            return eq;
        }catch (Exception e){
            System.out.println("设置锁异常");
            return false;
        }

    }

}
