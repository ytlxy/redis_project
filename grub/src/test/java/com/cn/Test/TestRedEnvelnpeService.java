package com.cn.Test;

import com.cn.redis.service.IRedEnvelopeserice;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    public void testGrab(){
    }

}
