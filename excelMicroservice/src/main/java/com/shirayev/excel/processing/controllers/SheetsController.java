package com.shirayev.excel.processing.controllers;

import com.shirayev.excel.processing.dto.SheetsResponse;
import com.shirayev.excel.processing.dto.page.PageDto;
import com.shirayev.excel.processing.dto.page.PageRequestDto;
import com.shirayev.excel.processing.servicies.SheetsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sheet")
public class SheetsController {

    private final SheetsService sheetsService;

    @GetMapping("/all")
    public ResponseEntity<PageDto<SheetsResponse>> handlerGetSheets(PageRequestDto pageRequestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(sheetsService.getSheets(pageRequestDto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<SheetsResponse> handlerGetSheetById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(sheetsService.getSheetById(id));
    }

    @GetMapping("/byFile/{fileId}")
    public ResponseEntity<PageDto<SheetsResponse>> handlerGetSheetsByFileId(@PathVariable Long fileId, PageRequestDto pageRequestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(sheetsService.getSheetsByFileId(fileId, pageRequestDto));
    }
}
