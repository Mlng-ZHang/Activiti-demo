package com.zm.demo.filter;

import com.zm.demo.common.exception.CustomException;
import com.zm.demo.common.response.ErrorCode;
import com.zm.demo.config.JwtConfig;
import com.zm.demo.utils.JwtTokenUtils;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * @Name: JwtAuthTokenFilter
 * @Author: zhangming
 * @Date 2020/8/11 18:42
 * @Description:
 */
@Slf4j
@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    public static final String REFRESH_TOKEN = "/token/refresh";

    @Value("#{'${route.ignore}'.split(',')}")
    private String[] routeIgnores;

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 忽略url
        if(Arrays.asList(routeIgnores).contains(request.getRequestURI())){
            chain.doFilter(request, response);
            return;
        }
        try {
            // 获取header
            String authHeader = request.getHeader(JwtTokenUtils.AUTH_HEADER_KEY);
            if (StringUtils.isBlank(authHeader) || !authHeader.startsWith(JwtTokenUtils.TOKEN_PREFIX)) {
                log.error("### 用户未登录，请先登录 ###");
                resolver.resolveException(request, response, null, new CustomException(ErrorCode.USER_NOT_LOGGED_IN));
                return;
            }
            // 获取token
            final String token = authHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
            // 验证token并获取用户名
            String username = JwtTokenUtils.getUsername(token, jwtConfig.getSecret());
            // 放入security中
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            chain.doFilter(request, response);
        } catch (ExpiredJwtException e){
            // 1、放过刷新token的url请求
            if(REFRESH_TOKEN.equals(request.getRequestURI())){
                chain.doFilter(request, response);
                return;
            }
            // 否则抛出异常，由全局异常处理
            resolver.resolveException(request, response, null, new CustomException(ErrorCode.PERMISSION_TOKEN_EXPIRED));
        }
    }
}
