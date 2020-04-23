package com.cn.test;

import com.cn.redis.util.redisLockutli;
import com.cn.redis.util.springutli;

public class Test1 implements Runnable{
    private static volatile Integer times=0;
    public static void dolask(){
        redisLockutli redisLock=(redisLockutli) springutli.getBean("redisLock");
        boolean b=redisLockutli.tryLockWithtimeout(10,100);
        if (b==false){
            return ;
        }
        while (times<=20){
            System.out.println(times++ +"id:"+Thread.currentThread().getId());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void run() {
        Test1.dolask();
    }
}
