package com.shirayev.statistics_people_passage.client_micro_service;

import com.shirayev.statistics_people_passage.client_micro_service.uri.URIBuilder;
import com.shirayev.statistics_people_passage.dto.FileNesting;
import com.shirayev.statistics_people_passage.dto.page.PageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
@Qualifier("excelProcessingClient")
public class ExcelProcessingClientMicroService implements ClientMicroService<PageDto<FileNesting>>{

    private final RestOperations restTemplate;
    @Override
    public PageDto<FileNesting> getData(URI uriLine) {
        return restTemplate.exchange(uriLine,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDto<FileNesting>>(){}).getBody();
    }
}
