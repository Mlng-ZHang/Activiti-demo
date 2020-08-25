package com.zm.demo.custom.command;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;

/**
 * @Name: ActivitiCustomCommand
 * @Author: zhangming
 * @Date 2020/8/20 16:25
 * @Description: 自定义command命令类
 */
@Slf4j
public class ActivitiCustomCommand implements Command {

    @Override
    public Object execute(CommandContext commandContext) {
        log.info("====================[CustomCmd]=======================");
        log.info("====================Hello World!======================");
        log.info("====================[CustomCmd]=======================");
        return null;
    }

}
