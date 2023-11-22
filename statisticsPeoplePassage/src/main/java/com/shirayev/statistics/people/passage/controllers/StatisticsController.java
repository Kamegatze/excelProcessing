package com.shirayev.statistics.people.passage.controllers;


import com.shirayev.statistics.people.passage.servicies.FileService;
import com.shirayev.statistics.people.passage.dto.FileDto;
import com.shirayev.statistics.people.passage.dto.FileNesting;
import com.shirayev.statistics.people.passage.model.CountPeoplePassageByAction;
import com.shirayev.statistics.people.passage.servicies.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.util.List;

@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    private final FileService fileService;

    @GetMapping
    public ResponseEntity<List<CountPeoplePassageByAction>> handlerCountPeoplePassageByAction(@RequestParam Time start, @RequestParam Time end) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(statisticsService.handlerGetStatisticsByActionAndAge(start, end));
    }

    @PostMapping("/save")
    public ResponseEntity<FileDto> handlerSaveFile(@RequestBody FileNesting fileNesting) {
        FileDto fileDto = fileService.saveNesting(fileNesting);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(fileDto);
    }

}
