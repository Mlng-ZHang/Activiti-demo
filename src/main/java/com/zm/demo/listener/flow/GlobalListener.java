package com.zm.demo.listener.flow;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.el.FixedValue;
import org.activiti.engine.impl.persistence.entity.ExecutionEntityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

/**
 * @Name: GlobalListener
 * @Author: zhangming
 * @Date 2020/8/18 17:39
 * @Description: 全局监听器
 */
@Slf4j
public class GlobalListener implements ExecutionListener {


    /**
     * 注入属性，对应标签 <activiti:field name="msg">
     */
    @Getter
    @Setter
    private FixedValue msg;

    @Override
    public void notify(DelegateExecution execution) {
        log.info("This is the GlobalListener");
        switch (execution.getEventName()){
            case EVENTNAME_START:
                log.info("================Start=====================");break;
            case EVENTNAME_TAKE:
                log.info("================Take=====================");break;
            case EVENTNAME_END:
                log.info("================End=====================");break;
        }
        log.info("ProcessInstanceId is : {} , inject var is {}", execution.getProcessInstanceId(), msg.getValue(execution));
        RepositoryService repositoryService = Context.getProcessEngineConfiguration().getRepositoryService();

        ProcessDefinition pdf = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(execution.getProcessDefinitionId())
                .singleResult();

        ProcessInstance pi = ((ExecutionEntityImpl) execution).getProcessInstance();
        log.info("ProcessDefinition info is : {}", pdf);
        log.info("Instance info is : {}", pi);
        log.info("=====================================================");
    }

}
