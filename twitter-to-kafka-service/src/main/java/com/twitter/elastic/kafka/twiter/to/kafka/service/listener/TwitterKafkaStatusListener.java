package com.twitter.elastic.kafka.twiter.to.kafka.service.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.twitter.elastic.kafka.config.KafkaConfigData;
import com.twitter.elastic.kafka.kafka.avro.model.TwitterAvroModel;
import com.twitter.elastic.kafka.kafka.producer.config.service.KafkaProducer;
import com.twitter.elastic.kafka.twiter.to.kafka.service.mapper.TwitterStatusToAvroMapper;
import twitter4j.Status;
import twitter4j.StatusAdapter;

@Component
public class TwitterKafkaStatusListener extends StatusAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterKafkaStatusListener.class);

    private final KafkaConfigData kafkaConfigData;
    
    private final KafkaProducer<Long, TwitterAvroModel> kafkaProducer;
    
    private final TwitterStatusToAvroMapper twitterStatusToAvroMapper;
    
    public TwitterKafkaStatusListener(
        final KafkaConfigData kafkaConfigData,
        final KafkaProducer<Long, TwitterAvroModel> kafkaProducer,
        final TwitterStatusToAvroMapper twitterStatusToAvroMapper) {
        this.kafkaConfigData = kafkaConfigData;
        this.kafkaProducer = kafkaProducer;
        this.twitterStatusToAvroMapper = twitterStatusToAvroMapper;
    }
    
    @Override
    public void onStatus(Status status) {
        String topicName = kafkaConfigData.getTopicName();
        LOG.info("Twitter status with text {} to kafka topic {} ",
                 status.getText(),
                 topicName);
    
        TwitterAvroModel twitterAvroModel = twitterStatusToAvroMapper.twitterStatusToAvroModel(status);
    
        kafkaProducer.send(topicName, twitterAvroModel.getUserId(), twitterAvroModel);
    }
}
