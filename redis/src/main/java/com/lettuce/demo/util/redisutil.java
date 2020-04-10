package com.lettuce.demo.util;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.support.ConnectionPoolSupport;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.util.function.Supplier;

public class redisutil {
    public static final String REDIS_ADDRESS="redis://redis@redis/0";
    private static final int MAX_IDLE = 11;
    private static final int MIN_IDLE = 1;
    private static final int MAX_TOTAL = 1;
    private static final boolean TEST_ON_BORROW = true;
    private static final RedisURI REDIS_URI=RedisURI.create(REDIS_ADDRESS);
    private static final RedisClient REDIS_CLIENT= RedisClient.create(REDIS_URI);
    private static GenericObjectPool<StatefulRedisConnection<String,String>> pool;
    private static final ThreadLocal<StatefulRedisConnection> REDIS_CONNECTION_THREAD_LOCAL=new ThreadLocal<StatefulRedisConnection>();

    static {
        GenericObjectPoolConfig config=new GenericObjectPoolConfig();
        config.setMinIdle(MAX_IDLE);
        config.setMinIdle(MIN_IDLE);
        config.setMaxTotal(MAX_TOTAL);
        config.setTestOnBorrow(TEST_ON_BORROW);
//        pool = ConnectionPoolSupport.createGenericObjectPool(()->redisutil.getConnection(),config);
    pool=ConnectionPoolSupport.createGenericObjectPool(new Supplier<StatefulRedisConnection<String, String>>() {
        @Override
        public StatefulRedisConnection<String, String> get() {
//            return redisutil.getConnection();
            return REDIS_CLIENT.connect();
        }
    },config);
    }
    public static RedisClient getRedisClient(){

        return REDIS_CLIENT;
    }
    public static StatefulRedisConnection getConnection() {
        StatefulRedisConnection<String,String> connection=REDIS_CONNECTION_THREAD_LOCAL.get();
        if(connection==null){
            connection=build();
            REDIS_CONNECTION_THREAD_LOCAL.set(connection);
        }
        return connection;
    }
    public static void close(){
        StatefulRedisConnection<String,String> connection=REDIS_CONNECTION_THREAD_LOCAL.get();
        if(connection==null){
            connection.close();
            REDIS_CONNECTION_THREAD_LOCAL.remove();
        }
    }
    private static StatefulRedisConnection build(){
        try {
            return pool.borrowObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
