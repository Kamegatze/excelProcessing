package com.shirayev.excel_processing.controllers;

import com.shirayev.excel_processing.dto.SheetsDto;
import com.shirayev.excel_processing.servicies.SheetsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sheet")
@RequiredArgsConstructor
public class SheetsController {

    private final SheetsService sheetsService;

    @GetMapping("/{id}")
    public ResponseEntity<SheetsDto> handlerGetSheetById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(sheetsService.getSheetById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<SheetsDto>> handlerGetSheets() {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(sheetsService.getSheets());
    }

}
