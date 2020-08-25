package com.zm.demo.controller;

import com.zm.demo.common.response.Result;
import com.zm.demo.dto.LoginReq;
import com.zm.demo.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Name: LoginController
 * @Author: zhangming
 * @Date 2020/8/12 16:40
 * @Description:
 */
@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private SysUserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginReq loginReq){
        return userService.login(loginReq);
    }
}
