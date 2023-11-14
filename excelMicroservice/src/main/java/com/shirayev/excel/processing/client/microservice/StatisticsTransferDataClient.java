package com.shirayev.excel.processing.client.microservice;

import com.shirayev.excel.processing.client.microservice.builder.BuilderStatisticsTransfer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;

import java.net.URI;

@Data
@Component
@RequiredArgsConstructor
public class StatisticsTransferDataClient<T> implements TransferDataClient<T> {

    private final RestOperations restTemplate;

    private BuilderStatisticsTransfer<T> builderStatisticsTransfer;

    @Override
    public T transferData(Object body) {
        return restTemplate.exchange(builderStatisticsTransfer.getUri(),
                HttpMethod.POST,
                new HttpEntity<>(body),
                builderStatisticsTransfer.getClazz()).getBody();
    }
}
