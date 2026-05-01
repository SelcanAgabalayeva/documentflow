package com.selcan.documentflow.integration.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.integration.handler.advice.RequestHandlerRetryAdvice;


@Configuration
public class RetryConfig {

    @Bean
    public RequestHandlerRetryAdvice retryAdvice() {
        return new RequestHandlerRetryAdvice();
    }
}
