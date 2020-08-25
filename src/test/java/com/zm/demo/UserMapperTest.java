package com.zm.demo;

import com.zm.demo.db.mapper.SysUserMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserMapperTest extends BaseTest{

    @Autowired
    private SysUserMapper userMapper;

    @Test
    public void selectByUserName(){
        System.out.println(userMapper.selectAllByUserName("admin"));
    }
}
