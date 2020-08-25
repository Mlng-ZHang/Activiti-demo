package com.zm.demo.custom.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.impl.interceptor.AbstractCommandInterceptor;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;

/**
 * @Name: ActivitiCustomPostInterceptor
 * @Author: zhangming
 * @Date 2020/8/20 16:49
 * @Description: 自定义后置处理器
 */
@Slf4j
public class ActivitiCustomPostInterceptor extends AbstractCommandInterceptor {
    @Override
    public <T> T execute(CommandConfig config, Command<T> command) {
        log.info("===============CustomPostInterceptor Start===================");
        try {
            return next.execute(config, command);
        } finally {
            log.info("===============CustomPostInterceptor End===================");
        }
    }
}
