package com.twitter.elastic.kafka.twiter.to.kafka.service.mapper;

import org.springframework.stereotype.Component;

import com.twitter.elastic.kafka.kafka.avro.model.TwitterAvroModel;
import twitter4j.Status;

@Component
public class TwitterStatusToAvroMapper {
    
    public TwitterAvroModel twitterStatusToAvroModel(Status status) {
        return TwitterAvroModel
            .newBuilder()
            .setId(status.getId())
            .setUserId(status.getUser().getId())
            .setText(status.getText())
            .setCreatedAt(status.getCreatedAt().getTime())
            .build();
    }
    
}
