package com.cn.redis.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration//说明是配置类
@PropertySource("classpath:config/redis.properties")//专门查找properties配置文件的路径
public class SpringDataRedisConfig {
    @Bean("redisConfiguration")//将Bean对象交与spring管理
    public RedisStandaloneConfiguration getRedisConfiguration(//redis独立配置
     @Value("${redis.host}") String hostName,
     @Value("${redis.port}") int port,
     @Value("${redis.auth}") String password,
     @Value("${redis.database}") int database
    ){
        RedisStandaloneConfiguration configuration=new RedisStandaloneConfiguration();
        configuration.setHostName(hostName);
        configuration.setPort(port);
        configuration.setPassword(password);
        configuration.setDatabase(database);
        return configuration;
    }
    @Bean("objectPoolConfig")
    public GenericObjectPoolConfig getObjectPoolconfig(//对象池配置
    @Value(value = "${redis.pool.maxTotal}") int maxTotal,
    @Value(value = "${redis.pool.maxIdle}") int maxIdle,
    @Value(value = "${redis.pool.minIdle}") int minIdle,
    @Value(value = "${redis.pool.testOnBorrow}") boolean testOnBorrow
    ){
        GenericObjectPoolConfig poolConfig=new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        poolConfig.setMaxTotal(maxTotal);
        poolConfig.setTestOnBorrow(testOnBorrow);
        return poolConfig;
    }
    @Bean("lettuceClientConfiguration")
    public LettuceClientConfiguration getLettuceClientConfiguration(//Lettuce客户端配置
    @Autowired GenericObjectPoolConfig poolConfig
    ){
        return LettucePoolingClientConfiguration.builder().poolConfig(poolConfig).build();
    }
    @Bean("redisConnectionFactory")
    public RedisConnectionFactory getConnectionFactory(//redis连接工厂
    @Autowired RedisStandaloneConfiguration redisconfiguration,
    @Autowired LettuceClientConfiguration lettuceClientConfiguration
    ){
        LettuceConnectionFactory connectionFactory=new
                LettuceConnectionFactory(redisconfiguration,lettuceClientConfiguration);
        return connectionFactory;
    }
    @Bean("redisTemplate")
    public RedisTemplate getRedisTemplate(//redis模板
                                          @Autowired RedisConnectionFactory connectionFactory
    ){
        RedisTemplate<String,String> redisTemplate=new RedisTemplate<String, String>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(Object.class));
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer(Object.class));
        return redisTemplate;
    }
    @Bean("stringRedisTemplate")
    public StringRedisTemplate getStringRedisTemplate(@Autowired RedisConnectionFactory connectionFactory){//string类型的redis模板
        StringRedisTemplate redisTemplate=new StringRedisTemplate();
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }
}
