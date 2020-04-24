package com.cn.redis.config;

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
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@PropertySource("classpath:config/redis.properties")
public class SpringDataRedisConfig {
    @Bean("redisConfiguration")
    public RedisStandaloneConfiguration getRedisConfiguration(
            @Value(value = "${redis.host}") String hostName,
            @Value(value = "${redis.port}") int port,
            @Value(value = "${redis.auth}") String password,
            @Value(value = "${redis.database}") int database
            ){
        RedisStandaloneConfiguration configuration=new RedisStandaloneConfiguration();
        configuration.setHostName(hostName);
        configuration.setPort(port);
        configuration.setPassword(RedisPassword.of(password));
        configuration.setDatabase(database);
        return configuration;
    }
    @Bean("objectPoolConfig")
    public GenericObjectPoolConfig getObjectPoolConfig(
            @Value(value = "${redis.pool.maxTotal}") int maxTotal,
            @Value(value = "${redis.pool.maxIdle}") int maxIdle,
            @Value(value = "${redis.pool.minIdle}") int minIdle,
            @Value(value = "${redis.pool.testOnBorrow}") boolean testOnBorrow
    ){
        GenericObjectPoolConfig config=new GenericObjectPoolConfig();
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        return config;
    }
    @Bean("lettuceClientConfiguration")
    public LettuceClientConfiguration getLettuceClientConfiguration(
        @Autowired GenericObjectPoolConfig config
    ){
        return LettucePoolingClientConfiguration.builder().poolConfig(config).build();
    }
    @Bean("connectionFactory")
    public RedisConnectionFactory getConnectionFactory(
            @Autowired RedisStandaloneConfiguration redisConfiguration,
            @Autowired LettuceClientConfiguration lettuceClientConfiguration
    ){
        LettuceConnectionFactory connectionFactory=new LettuceConnectionFactory(redisConfiguration,lettuceClientConfiguration);
        return connectionFactory;
    }
    @Bean("redisTemplate")
    public RedisTemplate getRedisTemplate(
            @Autowired RedisConnectionFactory ConnectionFactory
    ){
        RedisTemplate<String,Object> redisTemplate=new RedisTemplate<String, Object>();
        redisTemplate.setConnectionFactory(ConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer(Object.class));
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer(Object.class));
        return redisTemplate;
    }
    @Bean("stringRedisTemplate")
    public RedisTemplate getStringRedisTemplate(
            @Autowired RedisConnectionFactory connectionFactory
    ){
        StringRedisTemplate stringRedisTemplate=new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(connectionFactory);
        return stringRedisTemplate;
    }

}
