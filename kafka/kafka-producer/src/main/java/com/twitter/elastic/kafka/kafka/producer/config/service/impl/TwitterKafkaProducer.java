package com.twitter.elastic.kafka.kafka.producer.config.service.impl;


import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.twitter.elastic.kafka.kafka.avro.model.TwitterAvroModel;
import com.twitter.elastic.kafka.kafka.producer.config.service.KafkaProducer;
import jakarta.annotation.PreDestroy;

@Service
public class TwitterKafkaProducer implements KafkaProducer<Long, TwitterAvroModel> {
    
    private static final Logger LOG = LoggerFactory.getLogger(TwitterKafkaProducer.class);
    
    private KafkaTemplate<Long, TwitterAvroModel> kafkaTemplate;
    
    public TwitterKafkaProducer(KafkaTemplate<Long, TwitterAvroModel> template) {
        this.kafkaTemplate = template;
    }
    
    @Override
    public void send(String topicName, Long key, TwitterAvroModel message) {
        LOG.info("Sending message='{}' to topic='{}'", message, topicName);
        
        kafkaTemplate.send(topicName, key, message)
                     .whenComplete((result, throwable) ->
                                       Optional.ofNullable(throwable)
                                               .ifPresentOrElse(th -> LOG.error("Error while sending message {} to topic {}", message, topicName, th),
                                                                () -> {
                                                                    RecordMetadata metadata = result.getRecordMetadata();
                                                                    LOG.debug(
                                                                        "Received new metadata. Topic: {}; Partition {}; Offset {}; Timestamp {}, at time {}",
                                                                        metadata.topic(),
                                                                        metadata.partition(),
                                                                        metadata.offset(),
                                                                        metadata.timestamp(),
                                                                        System.nanoTime());
                                                                }));
    }
    
    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            LOG.info("Closing kafka producer!");
            kafkaTemplate.destroy();
        }
    }
    
}
