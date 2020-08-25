package com.zm.demo.db.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zm.demo.db.entity.SysUser;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangming
 * @since 2020-08-11
 */
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * 根据用户名查询用户所有信息
     * @param username
     * @return
     */
    SysUser selectAllByUserName(String username);

    /**
     * 根据用户名查询用户基本信息
     * @param username
     * @return
     */
    SysUser selectBaseByUserName(String username);
}
