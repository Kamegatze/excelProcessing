package com.shirayev.excel_processing.controllers;

import com.shirayev.excel_processing.dto.PeoplePassageDto;
import com.shirayev.excel_processing.dto.page.PageDto;
import com.shirayev.excel_processing.dto.page.PageRequestDto;
import com.shirayev.excel_processing.servicies.PeoplePassageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/people_passage")
@RequiredArgsConstructor
public class PeoplePassageController {

    private final PeoplePassageService peoplePassageService;

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
