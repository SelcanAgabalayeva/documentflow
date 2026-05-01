package com.selcan.documentflow.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class IntegrationChannelsConfig {

    @Bean
    public TaskExecutor workflowTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(6);
        executor.setQueueCapacity(20);
        executor.setThreadNamePrefix("workflow-");
        executor.initialize();
        return executor;
    }

    @Bean
    public MessageChannel documentSubmissionChannel(TaskExecutor workflowTaskExecutor) {
        return new ExecutorChannel(workflowTaskExecutor);
    }

    @Bean
    public MessageChannel emailNotificationChannel(TaskExecutor workflowTaskExecutor) {
        return new ExecutorChannel(workflowTaskExecutor);
    }

    @Bean
    public MessageChannel approvalDecisionChannel(TaskExecutor workflowTaskExecutor) {
        return new ExecutorChannel(workflowTaskExecutor);
    }

    @Bean
    public MessageChannel auditChannel() {
        return new PublishSubscribeChannel();
    }
}