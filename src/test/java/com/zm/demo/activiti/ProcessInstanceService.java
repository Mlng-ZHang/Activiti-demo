package com.zm.demo.activiti;

import com.zm.demo.ActivitiTest;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @Name: ProcessInstanceService
 * @Author: zhangming
 * @Date 2020/8/18 10:39
 * @Description: 流程实例
 */
public class ProcessInstanceService extends ActivitiTest {

    @Autowired
    private RuntimeService runtimeService; // 运行时相关操作


    public ProcessInstance queryInstance(){
        return runtimeService
                .createProcessInstanceQuery()
//                .processDefinitionKey("demo_process_1")
                .processDefinitionKey("time_service_demo")
                .singleResult();
    }
    /**
     * 启动流程实例
     *
     * 影响的表：
     * act_hi_actinst      已完成的活动信息
     * act_hi_identitylink   参与者信息
     * act_hi_procinst     流程实例
     * act_hi_taskinst     任务实例
     * act_ru_execution    执行表
     * act_ru_identitylink   参与者信息
     * act_ru_task   任务表
     */
    @Test
    public void testStartProcess() {
        Map<String, Object> formVar = new HashMap<>();
        formVar.put("userId", 1);
        formVar.put("userName", "张三");
        formVar.put("type", "病假");
        formVar.put("day", 2);
        // 这一步最好放在 listener去做判断与自动填充审核人
        formVar.put("auditor", "1班班长-赵雷");  //  -> ${auditor} -> act_hi_identitylink:USER_ID 字段
        formVar.put("reason", "发烧");

        // 启动流程实例
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("demo_process_1", formVar);

        System.out.println("流程实例ID：" + pi.getId());

        // 此时act_hi_actinst记录有： 1、发起流程 2、网关 3、班长审核
        // act_hi_procinst 生成一条实例
        // act_hi_taskinst 当前流程运行节点记录
        // act_hi_varinst  所有传递的字段信息,一个字段一条记录
        // act_ru_execution 2条信息，因为执行了2个节点的任务。开始任务，班长审核节点
    }

    @Test
    public void testStartProcess_1() {
        // 启动流程实例
//        ProcessInstance pi = runtimeService.startProcessInstanceByKey("process_1");
//        ProcessInstance pi = runtimeService.startProcessInstanceByKey("process_task_1");
        ProcessInstance pi = runtimeService.startProcessInstanceByKey("time_service_demo");
        System.out.println("流程实例ID：" + pi.getId());
    }


    /**
     * 激活/暂停运行中的流程
     */
    @Test
    public void testActiveProcessInstance(){

        ProcessInstance pis = queryInstance();

        // 流程是否是暂停的
        if(pis.isSuspended()){
            // 激活流程
            runtimeService.activateProcessInstanceById(pis.getProcessInstanceId());
            System.out.println("激活流程");
        }else {
            // 暂停流程
            runtimeService.suspendProcessInstanceById(pis.getProcessInstanceId());
            System.out.println("暂停流程");
        }
    }


    /**
     * 删除流程
     * act_ru_task: 清空相关记录
     * act_ru_execution：清空相关记录
     * act_hi_taskinst：DELETE_REASON_
     * act_hi_procinst：DELETE_REASON_
     * act_hi_actinst：DELETE_REASON_
     */
    @Test
    public void delete(){
        ProcessInstance pis = queryInstance();
        runtimeService.deleteProcessInstance(pis.getProcessInstanceId(), "流程设计异常");
    }

    @Test
    public void queryByInstanceId(){
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processInstanceId("f73fc72b-e140-11ea-aa3b-84fdd1fbfe1d")
                .singleResult();
        System.out.println(processInstance);
    }

}
