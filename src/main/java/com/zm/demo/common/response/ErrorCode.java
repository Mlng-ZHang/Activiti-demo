package com.zm.demo.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Name: ErrorCode
 * @Author: zhangming
 * @Date 2020/8/13 10:02
 * @Description:
 */
@AllArgsConstructor
public enum ErrorCode {

    /* 用户错误：20001-30000*/
    USER_NOT_LOGGED_IN(20001, "用户未登录，请先登录"),
    USER_LOGIN_ERROR(20002, "账号不存在或密码错误"),
    USER_ACCOUNT_FORBIDDEN(20003, "账号已被禁用"),
    USER_NOT_EXIST(20004, "用户不存在"),
    USER_HAS_EXISTED(20005, "用户已存在"),

    /* 权限错误：30001-40000 */
    PERMISSION_UNAUTHENTICATED(30001,"此操作需要登陆系统！"),
    PERMISSION_UNAUTHORISE(30002,"权限不足，无权操作！"),
    PERMISSION_EXPIRE(30003,"登录状态过期！"),
    PERMISSION_TOKEN_EXPIRED(30004, "token已过期"),
    PERMISSION_LIMIT(30005, "访问次数受限制"),
    PERMISSION_TOKEN_INVALID(30006, "无效token"),
    PERMISSION_SIGNATURE_ERROR(30007, "签名失败"),

    /* 参数错误 40001-50000*/
    PARAM_IS_INVALID(40001, "参数无效"),
    PARAM_IS_BLANK(40002, "参数为空"),
    PARAM_TYPE_BIND_ERROR(40003, "参数格式错误"),
    PARAM_NOT_COMPLETE(40004, "参数缺失"),

    /* 系统错误 50001-60000 */
    SYSTEM_INNER_ERROR(50001, "系统内部错误");

    /**
     * 操作码
     */
    @Getter
    int code;
    /**
     * 提示信息
     */
    @Getter
    String message;
}
