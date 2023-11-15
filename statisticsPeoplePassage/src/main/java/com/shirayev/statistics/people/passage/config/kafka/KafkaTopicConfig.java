package com.shirayev.statistics.people.passage.config.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String kafkaAddress;

    @Value(value = "${spring.kafka.topics.statistics.save-file}")
    private String statisticsSaveTopic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configuration = Map.of(
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress
        );
        return new KafkaAdmin(configuration);
    }

    @Bean
    public NewTopic topicStatisticsSaveFile() {
        return new NewTopic(statisticsSaveTopic, 1, (short) 1);
    }

}
