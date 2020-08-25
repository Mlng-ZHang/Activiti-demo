package com.zm.demo.activiti;

import com.zm.demo.ActivitiTest;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name: ActivitiTaskService
 * @Author: zhangming
 * @Date 2020/8/18 11:08
 * @Description:
 */
public class ActivitiTaskService extends ActivitiTest {

    @Autowired
    private TaskService taskService; // 任务相关操作

    @Autowired
    private TaskRuntime taskRuntime; // 新版api，整合权限控制

    public List<Task> queryTaskList(){
        return taskService.createTaskQuery()
                .processDefinitionKey("demo_process_1")
                // 查询已激活的
                //.active()
                // 查询已暂停的
                //.suspended()
                .list();
    }

    public Task queryTask(){
        return taskService.createTaskQuery()
                .processDefinitionKey("demo_process_1")
                // 查询已激活的
                //.active()
                // 查询已暂停的
                //.suspended()
                .singleResult();
    }

    /**
     * 任务查询
     */
    @Test
    public void testTaskQuery(){
        List<Task> list = queryTaskList();
        System.out.println(list);
        for (Task task : list){
            System.out.println(task.getId());
            System.out.println(task.getName());
            System.out.println(task.getOwner());
            System.out.println(task.getAssignee());
            System.out.println(task.getFormKey());
            // 查询任务状态  1-激活， 2-暂停  SuspensionState
            System.out.println(task.isSuspended());
        }
    }


    /**
     * 直接在指定节点到结束节点-完成任务
     */
    @Test
    public void testCompleteTask(){
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("demo_process_1")
                .taskAssignee("1班班长-赵雷")
                .singleResult();
        System.out.println(task);

        /**
         * 班长审核通过
         */
        Map<String, Object> var = new HashMap<>();
        var.put("auditMsg", true);
        // 最后一个节点
        taskService.complete(task.getId(), var); // 等同于taskService.complete(task.getId(), var, false)

        System.out.println(queryTask());
    }

    /**
     * 将任务委托给他人，修改act_ru_task表记录
     * 如果没有任务所有者(owner_),则将该字段置为任务的当前受理人
     * 委托状态(delegation_)变更为PENDING
     * 当前审批人(assignee_)变更为指定的userId对象
     *
     * 张三生病，委托李四帮忙填写一下请假单
     */
    @Test
    public void delegateTask(){
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("process_task_1")
                .taskAssignee("zhangsan")
                .singleResult();
        // 在taskListener中，设置了当前任务执行者为zhangsan,
        // 执行之后，会触发 tasklistener 中的assignment事件
        // act_ru_task表字段变化
        // OWNER_ 变为zhangsan ; ASSIGNEE_ 为 lisi ;DELEGATION_ 为PENDING
        taskService.delegateTask(task.getId(), "lisi");
    }

    /**
     * 被委托人(lisi)处理完成，节点处理人回转到委托人(zhangsan)
     * 只有在委托状态(delegation_)为PENDING才能执行
     * 委托状态(delegation_)变更为RESOLVED
     * 当前审批人(assignee_)变更为 委托人
     *
     * 被委托人(lisi)处理之后，无论什么结果，都会返回给委托人(zhangsan)再次处理
     */
    @Test
    public void resolveTask(){
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("process_task_1")
                .taskAssignee("lisi")
                .singleResult();
        Map<String, Object> var = new HashMap<>();
        var.put("id", 1);
        var.put("name", "张三");
        // 李四帮忙填写一下请假单,填完之后还会重新指派回张三
        // 因此又会触发 tasklistener 中的assignment事件
        // ASSIGNEE_ 重新变为 zhangsan ;DELEGATION_ 为 RESOLVED
        taskService.resolveTask(task.getId(), var);
    }

    /**
     * 完成任务
     */
    @Test
    public void complete(){
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("process_task_1")
                .taskAssignee("zhangsan")
                .singleResult();
        // 触发 taskListener中的 complete 事件 => 再触发 delete 事件
        // 触发 ExecutionListener中的 end 事件
        // 删除当前 act_ru_task 表记录，插入下一个流程 task 记录
        taskService.complete(task.getId());
    }

    /**
     * 与claim区别就是，不做任务是否已经被认领的检查
     */
    @Test
    public void testSetAssignee(){
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("process_task_1")
                .taskAssignee("lisi")
                .singleResult();
        taskService.setAssignee(task.getId(), "sss");
    }

    /**
     * 任务分派，前提是当前任务未被任何人认领，如果有认领，则会抛出异常
     * 即 act_ru_task 表记录中 ASSIGNEE_ 字段为空，才可以成功
     *
     * 场景：管理员可以将未认领的任务，指派出去
     */
    @Test
    public void testClaim(){
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("process_task_1")
                // 查找未被认领的任务
                .taskUnassigned()
                .singleResult();

        // 会触发 tasklistener 中的assignment事件
        // act_ru_task表字段变化: ASSIGNEE_ 变为 lisi
        taskService.claim(task.getId(), "lisi");
    }


    /**
     * 定时任务触发
     */
    @Test
    public void testTimeTask() throws InterruptedException {
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("time_service_demo")
                .singleResult();
        Map<String, Object> var = new HashMap<>();
        var.put("money", 15690.74);
        var.put("target", "风清扬");
        var.put("auditMsg", "核实无误，已扣除当月事假一天工资");
        taskService.complete(task.getId(), var);
        Thread.currentThread().join();
    }
}
