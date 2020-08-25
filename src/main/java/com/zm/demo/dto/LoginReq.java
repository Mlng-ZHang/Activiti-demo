package com.zm.demo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Name: LoginReq
 * @Author: zhangming
 * @Date 2020/8/12 14:21
 * @Description:
 */
@Data
public class LoginReq implements Serializable {

    /**
     * 用户名
     */
    private String username;

    /**
     * Aes加密后的密码
     */
    private String password;
}
