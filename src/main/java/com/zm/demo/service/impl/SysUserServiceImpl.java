package com.zm.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zm.demo.common.exception.CustomException;
import com.zm.demo.common.response.ErrorCode;
import com.zm.demo.common.response.Result;
import com.zm.demo.db.entity.SysUser;
import com.zm.demo.db.mapper.SysUserMapper;
import com.zm.demo.dto.LoginReq;
import com.zm.demo.security.JwtUser;
import com.zm.demo.service.SysUserService;
import com.zm.demo.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangming
 * @since 2020-08-11
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 登陆
     *
     * @param loginReq
     * @return
     */
    @Override
    public Result login(LoginReq loginReq) {
        String username = loginReq.getUsername();
        SysUser sysUser = userMapper.selectBaseByUserName(username);
        // 验证用户密码
        if(sysUser == null || !sysUser.getPassword().equals(loginReq.getPassword())){
            throw new CustomException(ErrorCode.USER_LOGIN_ERROR);
        }
        // 放入security context
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 获取用户登录token
        String token = tokenService.generateToken((JwtUser) userDetails);
        return Result.createSuccessResult(token);
    }
}
