package com.zm.demo.common.exception;

import com.zm.demo.common.response.ErrorCode;
import lombok.Data;
/**
 * @Name: CustomException
 * @Author: zhangming
 * @Date 2020/8/12 11:17
 * @Description:
 */
@Data
public class CustomException extends RuntimeException {

    private ErrorCode resultCode;

    public CustomException(ErrorCode resultCode){
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    public CustomException(ErrorCode resultCode, Throwable e){
        super(resultCode.getMessage(), e);
        this.resultCode = resultCode;
    }
}
