package com.zm.demo.listener.flow;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.el.FixedValue;

/**
 * @Name: CustomExecutionListener
 * @Author: zhangming
 * @Date 2020/8/19 11:22
 * @Description: 测试执行器监听器
 */
@Slf4j
public class CustomExecutionListener implements ExecutionListener {

    @Getter
    @Setter
    private FixedValue msg;

    @Override
    public void notify(DelegateExecution execution) {
        log.info("This is the CustomExecutionListener");
        switch (execution.getEventName()) {
            case EVENTNAME_START:
                log.info("================Start=====================");
                log.info("msg ==> {}",msg.getValue(execution));
                log.info("================Start=====================");
                break;
            case EVENTNAME_END:
                log.info("================End=====================");
                log.info("msg ==> {}",msg.getValue(execution));
                log.info("================End=====================");
                break;
        }

    }
}
