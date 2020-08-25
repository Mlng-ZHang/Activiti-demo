package com.zm.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "jwt")
@Component
public class JwtConfig {

    /**
     * 私钥
     */
    private String secret;

    /**
     * 过期时间
     */
    private String expiration;

    /**
     * 加密header
     */
    private String header;
}
