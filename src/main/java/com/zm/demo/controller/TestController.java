package com.zm.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/get")
    public String get(){
        return "测试成功";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/role/user")
    public String hasUserRole(){
        return "hasUserRole 测试成功";
    }

    @PreAuthorize("hasRole('TEST')")
    @GetMapping("/role/test")
    public String hasTestRole(){
        return "hasTestRole测试成功";
    }

    @PreAuthorize("hasAuthority('user_add_btn')")
    @GetMapping("/resource/userAdd")
    public String hasUserAddResource(){
        return "hasUserAddResource 测试成功";
    }

    @PreAuthorize("hasAuthority('user_delete_btn')")
    @GetMapping("/resource/userDelete")
    public String hasUserDeleteResource(){
        return "hasUserDeleteResource 测试成功";
    }
}
