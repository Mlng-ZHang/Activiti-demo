package com.zm.demo.activiti;

import com.zm.demo.ActivitiTest;
import org.activiti.engine.DynamicBpmnService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Name: DynamicBpService
 * @Author: zhangming
 * @Date 2020/8/18 16:24
 * @Description: 动态修改bpmn文件
 */
public class DynamicBpService extends ActivitiTest {

    @Autowired
    private DynamicBpmnService dynamicBpmnService;

    public void testChange(){
    }
}
