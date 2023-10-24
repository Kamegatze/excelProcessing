package com.shirayev.excel_processing.controllers;

import com.shirayev.excel_processing.dto.SheetsResponse;
import com.shirayev.excel_processing.servicies.SheetsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sheet")
public class SheetsController {

    private final SheetsService sheetsService;

    @GetMapping("/all")
    public ResponseEntity<List<SheetsResponse>> handlerGetSheets() {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(sheetsService.getSheets());
    }


    @GetMapping("/{id}")
    public ResponseEntity<SheetsResponse> handlerGetSheetById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(sheetsService.getSheetById(id));
    }

    @GetMapping("/byFile/{fileId}")
    public ResponseEntity<List<SheetsResponse>> handlerGetSheetsByFileId(@PathVariable Long fileId) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(sheetsService.getSheetsByFileId(fileId));
    }
}
