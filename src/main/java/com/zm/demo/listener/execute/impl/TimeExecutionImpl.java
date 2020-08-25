package com.zm.demo.listener.execute.impl;

import com.zm.demo.listener.execute.IExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.VariableScope;

import java.time.LocalDateTime;

/**
 * @Name: TimeExecutionImpl
 * @Author: zhangming
 * @Date 2020/8/20 11:39
 * @Description:
 */
public class TimeExecutionImpl implements IExecution {

    public static final String TIME = "dateTime";

    /**
     * 具体执行逻辑
     * @param var
     */
    @Override
    public void execute(VariableScope var) {
        DelegateTask task = (DelegateTask) var;
        // 一分钟以后执行
        task.setVariable(TIME, LocalDateTime.now().plusMinutes(1).toString());
    }
}
