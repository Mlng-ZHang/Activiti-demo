package com.zm.demo.custom.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.impl.interceptor.AbstractCommandInterceptor;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandConfig;

/**
 * @Name: ActivitiCustomPreInterceptor
 * @Author: zhangming
 * @Date 2020/8/20 16:49
 * @Description: 自定义前置处理器
 */
@Slf4j
public class ActivitiCustomPreInterceptor extends AbstractCommandInterceptor {
    @Override
    public <T> T execute(CommandConfig config, Command<T> command) {
        log.info("===============CustomPreInterceptor Start===================");
        try {
            return next.execute(config, command);
        } finally {
            log.info("===============CustomPreInterceptor End===================");
        }
    }
}
