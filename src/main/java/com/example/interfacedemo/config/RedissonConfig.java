package com.example.interfacedemo.config;

import com.example.interfacedemo.properties.RedisProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {

//    @Value("${spring.redis.host}")
//    private String host;
//    @Value("${spring.redis.port}")
//    private String port;

    @Autowired
    private RedisProperties redisProperties;
    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        // config.useClusterServers().addNodeAddress("redis://" + host + ":" + port); // 分片集群方式
        SingleServerConfig server = config.useSingleServer();
        config.setLockWatchdogTimeout(5 * 1000L);
        server.setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort());
//        server.setPassword(password);
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }
}
