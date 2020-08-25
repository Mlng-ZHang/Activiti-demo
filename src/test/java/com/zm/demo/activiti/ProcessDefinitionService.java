package com.zm.demo.activiti;

import com.zm.demo.ActivitiTest;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;

/**
 * @Name: ProcessDefinitionService
 * @Author: zhangming
 * @Date 2020/8/18 9:54
 * @Description: 流程的部署、查询、激活、暂停、下载操作
 */
public class ProcessDefinitionService extends ActivitiTest {

    @Autowired
    private RepositoryService repositoryService;


    public ProcessDefinition queryProcessDefinition(){
        return repositoryService
                .createProcessDefinitionQuery()
//                .deploymentId("1082acf4-e031-11ea-8b6c-84fdd1fbfe1d")
//                .deploymentId("96273c64-e1e7-11ea-b104-84fdd1fbfe1d")
                .deploymentId("1fd6d440-e2b2-11ea-b438-84fdd1fbfe1d")
                .singleResult();
    }

    /**
     *  部署流程
     *  影响的表
     *  act_re_deployment 部署信息
     *  act_re_procdef    流程定义的一些信息
     *  act_ge_bytearray  流程定义的bpmn文件以及png文件
     */
    @Test
    public void testDeployment(){

        Deployment deployment = repositoryService
                .createDeployment()
//                .key("test_deploy")
//                .key("test_deploy_2")
                .key("test_deploy_3")
//                .name("请假流程部署")
//                .name("表单流程部署1")
                .name("定时流程部署1")
//                .addInputStream("流程文件名字", inputStream-bpmn输入流)
//                .addClasspathResource("processes/demo.bpmn")
//                .addClasspathResource("processes/demo_test_task.bpmn")
                .addClasspathResource("processes/demo_timer.bpmn")
                .deploy();

        System.out.println(deployment.getId());
        System.out.println(deployment.getKey());
        System.out.println(deployment.getName());
    }


    /**
     * 查询部署的流程信息
     */
    @Test
    public void testQueryDeploymentList(){
        System.out.println(repositoryService.createDeploymentQuery().list());
    }

    /**
     * 查询部署的流程定义信息
     *
     *  查询 act_re_procdef
     */
    @Test
    public void testQueryProcessDefinition(){
        ProcessDefinition prodef = queryProcessDefinition();

        System.out.println(prodef); // ProcessDefinitionEntity[demo_process_1:1:10cd7196-e031-11ea-8b6c-84fdd1fbfe1d]
        System.out.println(prodef.getId()); // demo_process_1:1:10cd7196-e031-11ea-8b6c-84fdd1fbfe1d
        System.out.println(prodef.getKey()); // demo_process_1
        System.out.println(prodef.getName()); // 请假流程
    }


    /**
     * 激活/挂起所有实例
     */
    @Test
    public void testActivePf(){

        ProcessDefinition pdf = queryProcessDefinition();

        System.out.println("流程定义状态:" + pdf.isSuspended());

        /**
         * 第一个参数：
         * 第二个参数：是否激活和暂停所有实例
         * 第三个参数：是指生效时间，不能使用当前时间，因为实际执行是通过后台定时器去异步执行
         *           设置当前时间会导致，实际执行时间已经大于当前时间，导致不执行任务
         *          设置为Null后台会立即执行
         */
        // 最后一个参数
        if(pdf.isSuspended()){
            // 全部激活
            repositoryService.activateProcessDefinitionByKey(pdf.getKey(), true, null);
//            repositoryService.activateProcessDefinitionByKey(pdf.getKey(), false, null);
            System.out.println("激活成功");
        }else {
            // 全部挂起
            repositoryService.suspendProcessDefinitionByKey(pdf.getKey(), true, null);
//            repositoryService.suspendProcessDefinitionByKey(pdf.getKey(), false, null);
            System.out.println("挂起成功");
        }
    }

    /**
     * 删除流程定义
     */
    @Test
    public void delete(){
        ProcessDefinition pdf = queryProcessDefinition();
        // cascade:是否级联删除
        //         默认：false-只要存在流程实例时，会抛异常
        //         true-强制删除
//        repositoryService.deleteDeployment(pdf.getDeploymentId());
        repositoryService.deleteDeployment(pdf.getDeploymentId(), true);
    }

    /**
     * 下载
     */
    @Test
    public void download() throws IOException {
        ProcessDefinition pdf = queryProcessDefinition();
        // 如果有上传过png，则可以用 pdf.getDiagramResourceName() 获取图片名
        InputStream pngIs = repositoryService.getResourceAsStream(pdf.getDeploymentId(), pdf.getResourceName());
        OutputStream os = new FileOutputStream("D:\\workspace\\activiti-demo\\src\\main\\resources\\1.bpmn");
        IOUtils.copy(pngIs, os);
        os.close();
        pngIs.close();
    }


}
