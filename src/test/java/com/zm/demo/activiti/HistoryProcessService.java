package com.zm.demo.activiti;

import com.zm.demo.ActivitiTest;
import com.zm.demo.utils.ActivitiUtils;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Name: HistoryProcessService
 * @Author: zhangming
 * @Date 2020/8/19 16:07
 * @Description:
 */
@Slf4j
public class HistoryProcessService extends ActivitiTest {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private ProcessDiagramGenerator processDiagramGenerator;


    @Test
    public void testCreateImg(){
        String instanceId = "fbe05759-e1e7-11ea-97e2-84fdd1fbfe1d";
        //  获取流程实例, 查询 act_hi_procinst
        HistoricProcessInstance processInstance = historyService.createHistoricProcessInstanceQuery()
                .processInstanceId(instanceId)
                .singleResult();
        System.out.println(processInstance);
        // 根据流程对象获取流程对象模型
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        //查看已执行的节点集合
        //获取流程历史中已执行节点，并按照节点在流程中执行先后顺序排序
        // 查询历史流程， 查询 act_hi_actinst 表记录
        List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery()
                .processInstanceId(instanceId)
                .orderByHistoricActivityInstanceStartTime()
                .asc()
                .list();
        System.out.println(historicActivityInstanceList);
        // 已执行的节点ID集合(将historicActivityInstanceList中元素的activityId字段取出封装到executedActivityIdList)
        List<String> executedActivityIdList = historicActivityInstanceList.stream()
                .map(item -> item.getActivityId())
                .collect(Collectors.toList());
        //获取流程走过的线
        List<String> flowIds = ActivitiUtils.getHighLightedFlows(bpmnModel, historicActivityInstanceList);
        //输出图像，并设置高亮
        outputImg(bpmnModel, flowIds, executedActivityIdList);
    }


    public void outputImg(BpmnModel bpmnModel, List<String> flowIds, List<String> executedActivityIdList){
        try (InputStream is = processDiagramGenerator.generateDiagram(bpmnModel, executedActivityIdList, flowIds, "宋体", "宋体", "宋体", true)){
            FileUtils.copyInputStreamToFile(is, new File("D:\\workspace\\activiti-demo\\src\\main\\resources\\1.svg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
