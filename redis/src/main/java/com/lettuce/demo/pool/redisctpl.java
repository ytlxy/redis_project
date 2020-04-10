package com.lettuce.demo.pool;

import com.lettuce.demo.util.redisutil;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.function.Supplier;

public class redisctpl {
    private static final int MAX_IDLE=11;
    private static final int MIN_IDLE=1;
    private static final int MAX_TOTAL=1;
    private static final boolean TEST_ON_BORROW=true;

    public static void main(String[] args) throws Exception {
        GenericObjectPoolConfig config=new GenericObjectPoolConfig();
        config.setMaxIdle(MAX_IDLE);
        config.setMinIdle(MIN_IDLE);
        config.setMaxTotal(MAX_TOTAL);
        config.setTestOnBorrow(TEST_ON_BORROW);

//        GenericObjectPool<StatefulRedisConnection<String,String>> pool=ConnectionPoolSupport.createGenericObjectPool(()-> redisutil.getConnection(),config);
        GenericObjectPool<StatefulRedisConnection<String,String>> pool=ConnectionPoolSupport.createGenericObjectPool(new Supplier<StatefulRedisConnection<String, String>>() {
            public StatefulRedisConnection<String, String> get() {
                return redisutil.getConnection();
            }
        },config);
        for(int x=0;x<10;x++){
            StatefulRedisConnection<String,String>connection=pool.borrowObject();
            System.out.println("数据库对象:"+connection);
            System.out.println("测试连接ping:"+connection.sync().ping());
            redisutil.close();
        }
    }
}
