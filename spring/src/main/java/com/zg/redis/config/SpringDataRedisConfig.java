package com.zg.redis.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@PropertySource("classpath:config/redis.properties")
public class SpringDataRedisConfig {
    @Bean("redisConfiguration")
    public RedisStandaloneConfiguration getRedisConfiguration(
            @Value(value = "${Redis.host}") String hostName,
            @Value(value = "${Redis.port}") int port,
            @Value(value = "${Redis.auth}") String password,
            @Value(value = "${Redis.database}") int database
    ) {
        RedisStandaloneConfiguration configuration=new RedisStandaloneConfiguration();
        configuration.setHostName(hostName);
        configuration.setPort(port);
        configuration.setPassword(RedisPassword.of(password));
        configuration.setDatabase(database);
        return configuration;
    }
    @Bean("objectPoolConfig")
    public GenericObjectPoolConfig genericObjectPoolConfig(
            @Value(value = "${Redis.pool.maxTotal}") int maxTotal,
            @Value(value = "${Redis.pool.maxIdle}") int maxIdle,
            @Value(value = "${Redis.pool.minIdle}") int minIdle,
            @Value(value = "${Redis.pool.testOnBorrow}") boolean testOnBorrow
){
    GenericObjectPoolConfig poolConfig=new GenericObjectPoolConfig();
    poolConfig.setMaxIdle(maxIdle);
    poolConfig.setMinIdle(minIdle);
    poolConfig.setMaxTotal(maxTotal);
    poolConfig.setTestOnBorrow(testOnBorrow);
    return poolConfig;
    }
    @Bean("lettuceClientConfiguration")
    public LettuceClientConfiguration getLettuceClientConfiguration(@Autowired GenericObjectPoolConfig poolConfig){
        return LettucePoolingClientConfiguration.builder().poolConfig(poolConfig).build();
    }
    @Bean("redisConnectionFactory")
    public RedisConnectionFactory getConnectionFactory(
            @Autowired RedisStandaloneConfiguration redisConfiguration,
            @Autowired LettuceClientConfiguration lettuceClientConfiguration
    ){
        LettuceConnectionFactory connectionFactory=new LettuceConnectionFactory(redisConfiguration,lettuceClientConfiguration);
       return connectionFactory;
    }
    @Bean("stringRedisTemplate")
    public StringRedisTemplate getStringRedisTemplate(@Autowired RedisConnectionFactory connectionFactory){
        StringRedisTemplate redisTemplate=new StringRedisTemplate();
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }
    @Bean("redisTemplate")
    public RedisTemplate getRedisTemplate(@Autowired RedisConnectionFactory connectionFactory){
//        RedisTemplate<String,String> redisTemplate= new RedisTemplate<String,String>();
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<String,Object>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(Object.class));
//        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer(Object.class));
//        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        return redisTemplate;
    }
}