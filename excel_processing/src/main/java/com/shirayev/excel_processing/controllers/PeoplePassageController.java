package com.shirayev.excel_processing.controllers;

import com.shirayev.excel_processing.dto.PeoplePassageDto;
import com.shirayev.excel_processing.servicies.PeoplePassageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

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

    @PostMapping("/write_file")
    public ResponseEntity<Map<String, String>> handlerWriteFileInDatabase(@RequestParam MultipartFile file) {

        System.out.println(file.getOriginalFilename());

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("response", "the file was recorded"));
    }
}
