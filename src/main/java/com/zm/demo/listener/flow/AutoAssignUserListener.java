package com.zm.demo.listener.flow;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * @Name: AutoAssignUserListener
 * @Author: zhangming
 * @Date 2020/8/18 14:02
 * @Description: 自动分配审核人
 */
@Slf4j
public class AutoAssignUserListener implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        log.info("This is AutoAssignUserListener , params are {}", execution);
        switch (execution.getEventName()){
            case "一级审核":
                execution.setVariable("firstAudit", "李四");break;
        }
    }
}
