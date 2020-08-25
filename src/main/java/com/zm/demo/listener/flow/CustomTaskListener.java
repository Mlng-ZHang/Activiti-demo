package com.zm.demo.listener.flow;

import com.zm.demo.listener.execute.IExecution;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.el.FixedValue;

/**
 * @Name: CustomTaskListener
 * @Author: zhangming
 * @Date 2020/8/19 11:22
 * @Description: 测试任务监听器
 */
@Slf4j
public class CustomTaskListener implements TaskListener {

    @Getter
    @Setter
    private FixedValue msg;

    @Override
    public void notify(DelegateTask delegateTask) {
        log.info("This is the CustomTaskListener");
        switch (delegateTask.getEventName()) {
            case EVENTNAME_CREATE:
                log.info("================Create=====================");
                log.info("msg ==> {}",msg.getValue(delegateTask));
                // 事件开始时，设置执行人
//                delegateTask.setAssignee("zhangsan");
                log.info("================Create=====================");
                break;
            case EVENTNAME_ASSIGNMENT:
                log.info("================Assignment=====================");
                log.info("msg ==> {}",msg.getValue(delegateTask));
                log.info("================Assignment=====================");
                break;
            case EVENTNAME_COMPLETE:
                log.info("================Complete=====================");
                log.info("msg ==> {}",msg.getValue(delegateTask));
                try {
                    IExecution execution = (IExecution) getClass().getClassLoader().loadClass(String.valueOf(msg.getValue(delegateTask))).newInstance();
                    execution.execute(delegateTask);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                }
                log.info("================Complete=====================");
                break;
            case EVENTNAME_DELETE:
                log.info("================Delete=====================");
                log.info("msg ==> {}",msg.getValue(delegateTask));
                // 清空所有variables
//                delegateTask.removeVariables();
                log.info("================Delete=====================");
                break;
        }
    }
}
