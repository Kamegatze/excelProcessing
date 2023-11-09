package com.shirayev.excel_processing.client.microservice;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestOperations;

import java.net.URI;

@Configuration
@RequiredArgsConstructor
@Qualifier("statisticsTransfer")
public class StatisticsTransferDataClient<T> implements TransferDataClient<T> {

    private final RestOperations restTemplate;

    @Override
    public T transferData(URI uri, Object body, Class<T> clazz) {
        return restTemplate.exchange(uri,
                HttpMethod.POST,
                new HttpEntity<>(body),
                clazz).getBody();
    }
}
