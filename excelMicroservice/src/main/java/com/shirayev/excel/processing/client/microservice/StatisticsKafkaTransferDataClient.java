package com.shirayev.excel.processing.client.microservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class StatisticsKafkaTransferDataClient<T> implements TransferDataClient<T> {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final NewTopic topicSaveFileStatistics;

    @Override
    public T transferData(Object body) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topicSaveFileStatistics.name(), body);

        future.whenComplete((result, throwable) -> {
            if(throwable != null) {
                log.warn("Message was not sent. Error: {}", throwable.getMessage());
            } else {
                log.info("Sent message: {}, with offset: {}", body, result.getRecordMetadata().offset());
            }
        });

        return (T) body;
    }
}
