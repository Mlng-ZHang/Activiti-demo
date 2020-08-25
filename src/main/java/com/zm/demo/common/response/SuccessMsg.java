package com.zm.demo.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Name: SuccessMsg
 * @Author: zhangming
 * @Date 2020/8/13 10:13
 * @Description:
 */
@AllArgsConstructor
public enum SuccessMsg {
    /* 成功信息 */
    LOGIN_SUCCESS("登陆成功！"),

    USER_ADD_SUCCESS("用户添加成功！")
    ;

    @Getter
    private String msg;
}
