package com.zm.demo.listener.flow;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @Name: FlowNotifyListener
 * @Author: zhangming
 * @Date 2020/8/14 15:33
 * @Description:
 */
@Slf4j
public class FlowNotifyListener implements ExecutionListener {

    @Getter
    @Setter
    private String messages;


    @Override
    public void notify(DelegateExecution execution) {
        String eventName = execution.getEventName();
        log.info("This is the FlowNotifyListener , eventName is [{}], message is [{}]", eventName, messages);

        Map<String, Object> variables = execution.getVariables();

        Set<Map.Entry<String, Object>> entries = variables.entrySet();

        // 将关系集合entryset进行迭代，存放到迭代器中
        Iterator<Map.Entry<String, Object>> it2 = entries.iterator();
        while (it2.hasNext()) {
            // 获取Map.Entry关系对象me
            Map.Entry<String, Object> me = it2.next();
            // 通过关系对像获取key
            String key2 = me.getKey();
            // 通过关系对像获取value
            Object value2 = me.getValue();
            System.out.println("key:" + key2 + "-->value:" + value2.toString());
        }
        execution.setVariable("director","lizr");
        execution.setVariable("teacher","wangls");
    }
}
