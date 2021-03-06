package com.cn.Test;

import com.cn.redis.service.IRedEnvelopeserice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

@ContextConfiguration(locations = {"classpath:spring/*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestRedEnvelnpeService {
    @Autowired
    private IRedEnvelopeserice redEnvelopeserice;
    private String userid="li";
    @Test
    public void testadd()throws Exception{
        System.out.println(this.redEnvelopeserice.add(userid,5,88.88));
    }
    @Test
    public void testGrab()throws Exception{
        CountDownLatch latch=new CountDownLatch(20);
        for (int x=0;x<20;x++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("["+Thread.currentThread().getName()+"]开始抢红包"+redEnvelopeserice
                            .grab("red-li-1588062742803",Thread.currentThread().getName()));
                    latch.countDown();
                }
            },"Thread-"+x).start();
        }
        latch.await();
    }
    @Test
    public void testResult(){
        Map<String,Double> result=this.redEnvelopeserice.result("red-li-1588062742803");
        for(Map.Entry<String,Double> entry:result.entrySet()){
            System.out.println("[红包抢夺结果]用户名："+entry.getKey()+"红包金额:"+entry.getValue());
        }
    }

}
