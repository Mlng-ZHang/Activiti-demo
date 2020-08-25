package com.zm.demo;

import com.zm.demo.config.JwtConfig;
import com.zm.demo.utils.JwtTokenUtils;
import com.zm.demo.security.JwtUser;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class JwtTokenUtilsTest extends BaseTest {

    @Autowired
    JwtConfig jwtConfig;

    @Test
    public void generateToken(){
        JwtUser user = JwtUser.builder()
                .userid(1L)
                .username("张三")
                .build();
        String token = JwtTokenUtils.generateToken(user, jwtConfig);
        System.out.println(token);
    }

    @Test
    public void getUsername(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiLlvKDkuIkiLCJ1c2VyaWQiOiJNUSIsImNyZWF0ZVRpbWUiOjE1OTcxMzgwOTExMzUsImV4cCI6MzE5NDI3NjMwMn0.zp1Q5re0TXxOIo5mMTX3l0POnw8Oy6xIzenMrmikrVdK0n4FH9r1OznV0KjjL7BRmHD3EdkDMfApOdvPT3CZMQ";
        String username = JwtTokenUtils.getUsername(token, jwtConfig.getSecret());
        System.out.println(username);
    }

    @Test
    public void getUserId(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiLlvKDkuIkiLCJ1c2VyaWQiOiJNUSIsImNyZWF0ZVRpbWUiOjE1OTcxMzgwOTExMzUsImV4cCI6MzE5NDI3NjMwMn0.zp1Q5re0TXxOIo5mMTX3l0POnw8Oy6xIzenMrmikrVdK0n4FH9r1OznV0KjjL7BRmHD3EdkDMfApOdvPT3CZMQ";
        String userId = JwtTokenUtils.getUserId(token, jwtConfig.getSecret());
        System.out.println(userId);
    }
}
