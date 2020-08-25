package com.zm.demo.controller;

import com.zm.demo.common.response.Result;
import com.zm.demo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Name: TokenController
 * @Author: zhangming
 * @Date 2020/8/12 14:07
 * @Description:
 */
@RestController
@RequestMapping("/token")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @GetMapping("/refresh")
    public Result refreshToken(HttpServletRequest request){
        return tokenService.refreshToken(request);
    }
}
