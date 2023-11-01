package com.shirayev.statistics_people_passage.controllers;


import com.shirayev.statistics_people_passage.dto.page.PageRequestDto;
import com.shirayev.statistics_people_passage.model.AvgAgeGroupByActionStatisticsPeoplePassage;
import com.shirayev.statistics_people_passage.servicies.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("")
    public ResponseEntity<List<AvgAgeGroupByActionStatisticsPeoplePassage>> handlerAvgAgeByAction(@RequestParam Time start, @RequestParam Time end,
                                                                                                  PageRequestDto pageRequestDto,
                                                                                                  UriComponentsBuilder uri) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(statisticsService.handlerGetStatisticsByActionAndAge(start, end, pageRequestDto, uri));
    }

}
