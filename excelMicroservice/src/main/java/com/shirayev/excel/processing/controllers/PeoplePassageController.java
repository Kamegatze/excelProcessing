package com.shirayev.excel.processing.controllers;

import com.shirayev.excel.processing.dto.page.PageDto;
import com.shirayev.excel.processing.dto.page.PageRequestDto;
import com.shirayev.excel.processing.servicies.IPeoplePassageService;
import com.shirayev.excel.processing.dto.PeoplePassageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/people_passage")
@RequiredArgsConstructor
public class PeoplePassageController {

    private final IPeoplePassageService peoplePassageService;

    @GetMapping("/{id}")
    public ResponseEntity<PeoplePassageDto> handlerGetPeoplePassage(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(peoplePassageService.getPeoplePassageById(id));
    }

    @GetMapping("/by_sheet/{sheetId}")
    public ResponseEntity<PageDto<PeoplePassageDto>> handlerGetPeoplePassagesBySheetId(@PathVariable Long sheetId, PageRequestDto pageRequestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(peoplePassageService.getPeoplePassagesBySheet(sheetId, pageRequestDto));
    }

    @GetMapping("/all")
    public ResponseEntity<PageDto<PeoplePassageDto>> handlerGetPeoplePassages(PageRequestDto pageRequestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(peoplePassageService.getPeoplePassages(pageRequestDto));
    }
}
