package com.twitter.elastic.kafka.twiter.to.kafka.service.init;

import org.springframework.stereotype.Component;

import com.twitter.elastic.kafka.admin.client.KafkaAdminClient;
import com.twitter.elastic.kafka.config.KafkaConfigData;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class KafkaStreamInitializer implements StreamInitializer {
    
    private final KafkaConfigData kafkaConfigData;
    
    private final KafkaAdminClient kafkaAdminClient;
    
    public KafkaStreamInitializer(final KafkaConfigData kafkaConfigData,
                                  final KafkaAdminClient kafkaAdminClient) {
        this.kafkaConfigData = kafkaConfigData;
        this.kafkaAdminClient = kafkaAdminClient;
    }
    
    @Override
    public void init() {
        kafkaAdminClient.createTopics();
        kafkaAdminClient.checkSchemaRegistry();
        log.info("Topics {} are ready for operations ", kafkaConfigData.getTopicNamesToCreate());
    }
    
}
