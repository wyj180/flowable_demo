package com.study.flowable.helloworld;

import com.study.flowable.FlowableDemoApplicationTests;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.task.api.TaskQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Flowable 入门案例二  配置候选人组
 */
public class HelloWorld03 extends FlowableDemoApplicationTests {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    /**
     * 第一步：部署流程定义
     */
    @Test
    public void deploymentProcessDefinition() {
        Deployment deployment = repositoryService
                .createDeployment()// 创建一个部署对象
                .addClasspathResource("process/MyProcess2.bpmn")// 从classpath的资源中加载，一次只能加载一个文件
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
        String processDefinitionKey = "myProcess";

        ProcessInstance pi = runtimeService
                .startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例，key对应bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动

        System.out.println("流程实例ID:" + pi.getId());//流程实例ID    101
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());//流程定义ID   key01:1:4

        /**
         * 流程实例ID: 4806e50c-4bd5-11ea-b199-00ff0d67ef7e
         * 流程定义ID:helloworld:1:2504
         */
    }

    /**
     * 第二步：设置任选组
     */
    @Test
    public void setCandidateUsers() {
        String taskId = "c3f4605b-4bce-11ea-b88c-00ff0d67ef7e";
        String groupId = "user_group_id_001";
        taskService.addCandidateGroup(taskId, groupId);
    }

    /**
     * 候选组中的一个人 获取任务
     */
    @Test
    public void candidateGetTask() {
        TaskQuery taskQuery = taskService.createTaskQuery().taskCandidateOrAssigned("user_001");
        List<Task> tasks = taskQuery.list();
        for (Task task : tasks) {
            System.out.println("user_001正在处理的任务：" + task);
        }

        List<Task> tasks2 = taskService.createTaskQuery().taskCandidateOrAssigned("user_002").list();
        for (Task task : tasks2) {
            System.out.println("user_002正在处理的任务：" + task);
        }

        List<Task> tasks3 = taskService.createTaskQuery().taskCandidateOrAssigned("userC").list();
        for (Task task : tasks3) {
            System.out.println("userC正在处理的任务：" + task);
        }

        List<Task> tasks5 = taskService.createTaskQuery().taskCandidateOrAssigned("AAA").list();
        for (Task task : tasks5) {
            System.out.println("AAA正在处理的任务：" + task);
        }
    }

}
