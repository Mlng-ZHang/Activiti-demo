package com.zm.demo.activiti;


import com.zm.demo.ActivitiTest;
import com.zm.demo.custom.command.ActivitiCustomCommand;
import org.activiti.engine.ManagementService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class CommandTest extends ActivitiTest {

    @Autowired
    private ManagementService managementService;

    /**
     * 测试自定义command
     */
    @Test
    public void testCommand(){
        managementService.executeCommand(new ActivitiCustomCommand());
    }
}
