package com.zm.demo.listener.flow.serviceTask;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.impl.persistence.entity.TaskEntityImpl;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.concurrent.TimeUnit;

import static org.activiti.engine.delegate.BaseExecutionListener.EVENTNAME_END;
import static org.activiti.engine.delegate.BaseExecutionListener.EVENTNAME_START;


/**
 * @Name: AutoAssignMoneyServiceTask
 * @Author: zhangming
 * @Date 2020/8/20 9:49
 * @Description:
 */
@Slf4j
public class AutoAssignMoneyServiceTask implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        log.info("This is the AutoAssignMoneyServiceTask");
        log.info("assign msg is {}", execution.getVariables());
        log.info("start doing something...");
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("end doing something...");
    }
}
