package com.zm.demo.listener.execute.impl;

import com.zm.demo.listener.execute.IExecution;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.VariableScope;

/**
 * @Name: AuditExecutionImpl
 * @Author: zhangming
 * @Date 2020/8/20 11:11
 * @Description:
 */
@Slf4j
public class AuditExecutionImpl implements IExecution {

    public static final String VAR_KEY = "audit";
    public static final String VAR_VALUE_FIRST = "firstAudit";
    public static final String VAR_VALUE_SECOND = "secondAudit";

    /**
     * 具体执行逻辑
     *
     * @param var
     */
    @Override
    public void execute(VariableScope var) {
        DelegateTask task = (DelegateTask) var;
        switch (String.valueOf(var.getVariable(VAR_KEY))){
            case VAR_VALUE_FIRST:
                log.info("This is the First audit, task id is {}", task.getId());
                task.setVariable(VAR_VALUE_FIRST, "李四-by AuditExecutionImpl");
                break;
            case VAR_VALUE_SECOND:
                log.info("This is the Second audit, task id is {}", task.getId());
                task.setVariable(VAR_VALUE_SECOND, "赵六-by AuditExecutionImpl");
                break;
        }
    }
}
