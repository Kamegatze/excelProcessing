package com.shirayev.statistics_people_passage.controllers;

import com.shirayev.statistics_people_passage.entities.StatisticsPeoplePassage;
import com.shirayev.statistics_people_passage.repositories.StatisticsPeoplePassageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsPeoplePassageRepository statisticsPeoplePassageRepository;

    @GetMapping("")
    public ResponseEntity<Page<StatisticsPeoplePassage>> handlerAvgAgeByAction() {

        Page<StatisticsPeoplePassage> statisticsPeoplePassages = statisticsPeoplePassageRepository
                .findAllByTimeActionBetween(Time.valueOf("14:15:00"),
                        Time.valueOf("18:00:00"), PageRequest.of(0, 30));


        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(statisticsPeoplePassages);
    }

}
