package com.zm.demo.service;

import com.zm.demo.common.response.Result;
import com.zm.demo.security.JwtUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @Name: TokenService
 * @Author: zhangming
 * @Date 2020/8/12 13:36
 * @Description:
 */
public interface TokenService {

    /**
     * 刷新token
     * @param request
     * @return
     */
    Result refreshToken(HttpServletRequest request);


    /**
     * 生成token
     * @param jwtUser
     * @return
     */
    String generateToken(JwtUser jwtUser);
}
