package com.zm.demo.common.response;

import lombok.Data;

import java.io.Serializable;

/**
 * @Name: Result
 * @Author: zhangming
 * @Date 2020/8/12 10:35
 * @Description:
 */
@Data
public class Result<T> implements Serializable {

    /**
     * 成功码
     */
    public static final int SUCCESS_CODE = 200;

    /**
     * 状态code
     */
    private int code;

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    public static Result createErrorResult(ErrorCode errorCode) {
        Result result = new Result<>();
        result.setSuccess(false);
        result.setMessage(errorCode.getMessage());
        result.setCode(errorCode.getCode());
        return result;
    }

    public static <T> Result createSuccessResult(T data) {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setData(data);
        result.setCode(SUCCESS_CODE);
        return result;
    }

    public static <T> Result<T> createSuccessResult(T data, SuccessMsg msg) {
        Result<T> result = new Result<>();
        result.setSuccess(true);
        result.setData(data);
        result.setCode(SUCCESS_CODE);
        result.setMessage(msg.getMsg());
        return result;
    }
}
