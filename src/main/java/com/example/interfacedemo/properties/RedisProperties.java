package com.example.interfacedemo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author liyouxiang 2023/11/17
 */
@Component
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedisProperties {

    private String host;

    private String port;
}
