package com.zm.demo.security;

import com.zm.demo.db.entity.SysResource;
import com.zm.demo.db.entity.SysRole;
import com.zm.demo.db.entity.SysUser;
import com.zm.demo.db.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Name: JwtUserService
 * @Author: zhangming
 * @Date 2020/8/11 11:23
 * @Description:
 */
@Service
public class JwtUserService implements UserDetailsService {

    @Autowired
    private SysUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userMapper.selectAllByUserName(username);
        if(user == null){
            throw new UsernameNotFoundException(String.format("'%s'.这个用户不存在", username));
        }
        // 获取角色集合
        List<String> roles = user.getRoles().stream()
                .map(SysRole::getRolename)
                .collect(Collectors.toList());

        // 获取资源集合
        List<String> resources = user.getResources().stream()
                .map(SysResource::getResourceKey)
                .collect(Collectors.toList());
        ;
        List<SimpleGrantedAuthority> authorities = Stream.of(roles,resources)
                .flatMap(Collection::stream)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new JwtUser(user.getId(),user.getUsername(), user.getPassword(), authorities);
    }
}
