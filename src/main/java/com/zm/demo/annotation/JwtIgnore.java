package com.zm.demo.annotation;

import java.lang.annotation.*;

/**
 * @Name: JwtIgnore
 * @Author: zhangming
 * @Date 2020/8/11 17:49
 * @Description:
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface JwtIgnore {
}