package com.twitter.elastic.kafka.twiter.to.kafka.service;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.twitter.elastic.kafka.config.TwitterToKafkaServiceConfigData;
import com.twitter.elastic.kafka.twiter.to.kafka.service.init.StreamInitializer;
import com.twitter.elastic.kafka.twiter.to.kafka.service.runner.StreamRunner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@ComponentScan("com.twitter.elastic.kafka")
@Slf4j
@RequiredArgsConstructor
public class TwitterToKafkaApplicationService implements CommandLineRunner {
    private final StreamRunner streamRunner;
    
    private final StreamInitializer streamInitializer;
    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaApplicationService.class);
    }
    
    @Override
    public void run(final String... args) throws Exception {
        streamInitializer.init();
        streamRunner.start();
    }
    
}
