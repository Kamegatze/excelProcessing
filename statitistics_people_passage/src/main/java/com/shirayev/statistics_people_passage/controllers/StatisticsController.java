package com.shirayev.statistics_people_passage.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shirayev.statistics_people_passage.dto.page.PageDto;
import com.shirayev.statistics_people_passage.dto.StatisticsPeoplePassageDto;
import com.shirayev.statistics_people_passage.dto.page.PageRequestDto;
import com.shirayev.statistics_people_passage.entities.StatisticsPeoplePassage;
import com.shirayev.statistics_people_passage.repositories.StatisticsPeoplePassageRepository;
import com.shirayev.statistics_people_passage.servicies.StatisticsPeoplePassageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.sql.Time;
import java.util.Map;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsPeoplePassageService statisticsPeoplePassageService;

    private final RestTemplate restTemplate;

    private final String protocol = "http";

    private final  String port = "8080";

    private final String host = "localhost";

    private final String path = "api/people_passage";

    @GetMapping("")
    public ResponseEntity<PageDto<StatisticsPeoplePassageDto>> handlerAvgAgeByAction(PageRequestDto pageRequestDto,  UriComponentsBuilder uri) throws JsonProcessingException {

        URI uriLine = uri.scheme(protocol).host(host).port(port).path(path + "/all").query("pageNumber={pageNumber}&pageSize={pageSize}")
                .build(Map.of("pageNumber", pageRequestDto.getPageNumber(), "pageSize", pageRequestDto.getPageSize()));

        PageDto<StatisticsPeoplePassageDto> loadPage = restTemplate.exchange(uriLine,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDto<StatisticsPeoplePassageDto>>(){}).getBody();



        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(loadPage);
    }

}
