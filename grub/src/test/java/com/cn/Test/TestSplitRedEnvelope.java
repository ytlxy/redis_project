package com.cn.Test;

import com.cn.redis.util.SplitCheckUtil;
import com.cn.redis.util.SplitMoneyUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = {"classpath:spring/*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSplitRedEnvelope {
    private double money=888.88;
    private int amount=8;
    @Test
    public void Test()throws Exception{
        SplitMoneyUtil pmu=new SplitMoneyUtil(amount,money);
        System.out.println("拆分结果："+pmu.getAllPackages());
        System.out.println("验证："+ SplitCheckUtil.check(pmu.getAllPackages(),money));
    }
}
