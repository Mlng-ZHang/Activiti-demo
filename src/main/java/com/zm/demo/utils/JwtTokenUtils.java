package com.zm.demo.utils;

import com.alibaba.fastjson.JSON;
import com.zm.demo.common.exception.CustomException;
import com.zm.demo.common.response.ErrorCode;
import com.zm.demo.config.JwtConfig;
import com.zm.demo.constants.CommonConstants;
import com.zm.demo.security.JwtUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.Base64Codec;
import io.jsonwebtoken.impl.DefaultClaims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.util.Date;
import java.util.Map;

/**
 * @Name: JwtTokenUtil
 * @Author: zhangming
 * @Date 2020/8/11 13:58
 * @Description:
 */
@Slf4j
public class JwtTokenUtils {

    public static final String AUTH_HEADER_KEY = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";
    /**
     * 根据自定义参数生成token
     * @param claims
     * @param secret
     * @param expiration
     * @return
     */
    private static String generateToken(Map<String, Object> claims, String secret, long expiration) {
        Date expirationDate = new Date(System.currentTimeMillis() + expiration * 1000);
        return Jwts.builder()
                .setHeaderParam(JwsHeader.TYPE, JwsHeader.JWT_TYPE)
                .setHeaderParam(JwsHeader.ALGORITHM, SignatureAlgorithm.HS512.getValue())
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * 根据自定义参数生成token
     * @param claims
     * @param jwtConfig
     * @return
     */
    private static String generateToken(Map<String, Object> claims, JwtConfig jwtConfig) {
        return generateToken(claims, jwtConfig.getSecret(), DateUtils.calculateExpireTime(jwtConfig.getExpiration()));
    }

    /**
     * 根据用户信息生成token
     * @param jwtUser
     * @param jwtConfig
     * @return
     */
    public static String generateToken(JwtUser jwtUser, JwtConfig jwtConfig) {
        Claims claims = new DefaultClaims();
        claims.setSubject(jwtUser.getUsername());
        claims.put(CommonConstants.USER_ID, Base64Codec.BASE64URL.encode(jwtUser.getUserid()+""));
        claims.put(CommonConstants.CREATE_TIME, new Date());
        return generateToken(claims, jwtConfig);
    }

    /**
     * 获取token中的主体参数
     * @param token
     * @return
     */
    public static Claims getBodyFromToken(String token, String secret) {
        Claims claims = new DefaultClaims();
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e){
            log.error("jwt token [{}] is expired", token);
            throw e;
        } catch (Exception e) {
            log.error("parser token error : {}", ExceptionUtils.getStackTrace(e));
        }
        return claims;
    }

    /**
     * 获取用户名
     * @param token
     * @param secret
     * @return
     */
    public static String getUsername(String token, String secret) {
        return getBodyFromToken(token, secret).getSubject();
    }

    /**
     * 获取用户id
     * @param token
     * @param secret
     * @return
     */
    public static String getUserId(String token, String secret) {
        return Base64Codec.BASE64URL.decodeToString((String) getBodyFromToken(token, secret).get(CommonConstants.USER_ID));
    }

    /**
     * 判断token是否过期
     * @param token
     * @param secret
     * @return
     */
    public static Boolean isExpired(String token, String secret) {
        return getBodyFromToken(token, secret).getExpiration().before(new Date());
    }

    /**
     * 刷新token
     * @param token
     * @param jwtConfig
     * @return
     */
    public static String refreshToken(String token, JwtConfig jwtConfig) {
        return refreshToken(token, jwtConfig.getSecret(), DateUtils.calculateExpireTime(jwtConfig.getExpiration()));
    }

    public static Claims parseClaims(String token){
        String [] array = token.split("\\.");
        if(array.length != 3){
            throw new CustomException(ErrorCode.PERMISSION_TOKEN_INVALID);
        }
        String decode = Base64Codec.BASE64URL.decodeToString(array[1]);
        return JSON.parseObject(decode, DefaultClaims.class);
    }

    /**
     * 刷新token
     * @param token
     * @param secret
     * @param expiration
     * @return
     */
    public static String refreshToken(String token, String secret, long expiration) {
        Claims claims = parseClaims(token);
        claims.put(CommonConstants.CREATE_TIME, new Date());
        return generateToken(claims, secret, expiration);
    }
}
