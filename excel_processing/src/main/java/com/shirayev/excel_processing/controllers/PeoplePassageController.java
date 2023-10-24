package com.shirayev.excel_processing.controllers;

import com.shirayev.excel_processing.dto.PeoplePassageDto;
import com.shirayev.excel_processing.servicies.PeoplePassageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<PeoplePassageDto>> handlerGetPeoplePassagesBySheetId(@PathVariable Long sheetId) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(peoplePassageService.getPeoplePassagesBySheet(sheetId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<PeoplePassageDto>> handlerGetPeoplePassages() {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(peoplePassageService.getPeoplePassages());
    }
}
