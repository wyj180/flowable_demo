package com.study.flowable.serviceTask;

import com.study.flowable.FlowableDemoApplicationTests;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Flowable Demo, 使用ServiceTask
 *
 * 步骤：
 * 1、在画流程图时拖入ServiceTask，参考ServiceTaskProcess.bpmn中的serviceTask节点
 *    // <serviceTask activiti:delegateExpression="${eventServiceTask}" id="servicetask1" name="Service Task"></serviceTask>
 *    // activiti:delegateExpression="${eventServiceTask}" 表示使用eventServiceTask这个bean处理
 *
 * 2、定义eventServiceTask这个bean，需要实现JavaDelegate接口，参考ServiceTaskListener类
 *
 * 3、运行下面的useServiceTask()单元测试，即可看到效果
 *
 */
public class ServiceTaskDemo extends FlowableDemoApplicationTests {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Test
    public void useServiceTask() {
        // 部署流程
        deploymentProcessDefinition();

        // 启动流程实例
        startProcessInstance();

        // 处理任务
        String taskId = getTaskId();
        completeTask(taskId, "张三");
    }

    /**
     * 第一步：部署流程定义
     */
    @Test
    public void deploymentProcessDefinition() {
        Deployment deployment = repositoryService
                .createDeployment()// 创建一个部署对象
                .addClasspathResource("process/ServiceTaskProcess.bpmn")// 从classpath的资源中加载，一次只能加载一个文件
                .deploy();// 完成部署

        System.out.println("部署ID：" + deployment.getId());
        System.out.println("部署名称：" + deployment.getName());
    }

    /**
     * 第二步：启动流程实例
     */
    @Test
    public void startProcessInstance() {
        //根据流程定义的key启动流程
        String processDefinitionKey = "myServiceTaskProcess";

        ProcessInstance pi = runtimeService
                .startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例，key对应bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动

        System.out.println("流程实例ID:" + pi.getId());
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());
    }

    // 获取正在处理的任务ID
    public String getTaskId() {
        List<Task> tasks = taskService.createTaskQuery().list();
        String taskId = tasks.get(0).getId();
        return taskId;
    }

    // 根据taskId和处理人 完成任务
    public void completeTask(String taskId, String completeUser) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        // 设置处理人
        task.setAssignee(completeUser);
        // 完成任务
        taskService.complete(taskId);
    }

}
