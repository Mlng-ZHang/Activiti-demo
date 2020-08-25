package com.zm.demo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zm.demo.common.response.Result;
import com.zm.demo.db.entity.SysUser;
import com.zm.demo.dto.LoginReq;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangming
 * @since 2020-08-11
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 登陆
     * @param loginReq
     * @return
     */
    Result login(LoginReq loginReq);

}
