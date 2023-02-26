package com.twitter.elastic.kafka;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.twitter.elastic.kafka.config.RetryConfigData;

@Configuration
public class RetryConfig {
    
    private final RetryConfigData retryConfigData;
    
    public RetryConfig(final RetryConfigData retryConfigData) {
        this.retryConfigData = retryConfigData;
    }
    
    @Bean
    public RetryTemplate retryTemplate(){
        RetryTemplate retryTemplate = new RetryTemplate();
        ExponentialBackOffPolicy exponentialBackOffPolicy = new ExponentialBackOffPolicy();
        exponentialBackOffPolicy.setInitialInterval(retryConfigData.getInitialIntervalMs());
        exponentialBackOffPolicy.setMultiplier(retryConfigData.getMultiplier());
        exponentialBackOffPolicy.setMaxInterval(retryConfigData.getMaxIntervalMs());
        
        retryTemplate.setBackOffPolicy(exponentialBackOffPolicy);
        SimpleRetryPolicy simpleRetryPolicy = new SimpleRetryPolicy();
        simpleRetryPolicy.setMaxAttempts(retryConfigData.getMaxAttempts());
        retryTemplate.setRetryPolicy(simpleRetryPolicy);
        return retryTemplate;
    }
    
}
