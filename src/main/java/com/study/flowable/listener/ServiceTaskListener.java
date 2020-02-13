package com.study.flowable.listener;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

/**
 * ServiceTask的业务处理类
 */
@Component("eventServiceTask")
public class ServiceTaskListener implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("service Task 处理业务逻辑...");
    }
}
