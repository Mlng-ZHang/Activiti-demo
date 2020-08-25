package com.zm.demo.listener.execute;

import org.activiti.engine.delegate.VariableScope;

/**
 * @Name: AbstractExecution
 * @Author: zhangming
 * @Date 2020/8/20 11:05
 * @Description:
 */
public interface IExecution {

    /**
     * 具体执行逻辑
     */
    void execute(VariableScope var);
}
