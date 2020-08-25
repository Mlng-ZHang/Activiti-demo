package com.zm.demo.service.impl;

import com.zm.demo.common.response.Result;
import com.zm.demo.config.JwtConfig;
import com.zm.demo.security.JwtUser;
import com.zm.demo.utils.JwtTokenUtils;
import com.zm.demo.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Name: TokenServiceImpl
 * @Author: zhangming
 * @Date 2020/8/12 16:40
 * @Description:
 */
@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private JwtConfig jwtConfig;

    /**
     * 刷新token
     *
     * @param request
     * @return
     */
    @Override
    public Result refreshToken(HttpServletRequest request) {
        String header = request.getHeader(JwtTokenUtils.AUTH_HEADER_KEY);
        String oldToken = header.replace(JwtTokenUtils.TOKEN_PREFIX, "");
        String newToken = JwtTokenUtils.refreshToken(oldToken, jwtConfig);
        return Result.createSuccessResult(newToken);
    }

    /**
     * 生成token
     *
     * @param jwtUser
     * @return
     */
    @Override
    public String generateToken(JwtUser jwtUser) {
        return JwtTokenUtils.generateToken(jwtUser, jwtConfig);
    }

}
