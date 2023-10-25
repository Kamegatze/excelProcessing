package com.shirayev.excel_processing.controllers;


import com.shirayev.excel_processing.dto.FileDto;
import com.shirayev.excel_processing.dto.page.PageDto;
import com.shirayev.excel_processing.dto.page.PageRequestDto;
import com.shirayev.excel_processing.servicies.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AbstractPageRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;

    @PostMapping("/write_file")
    public ResponseEntity<Map<String, Object>> handlerWriteFileInDatabase(@RequestParam MultipartFile file, UriComponentsBuilder uri) throws IOException {

        FileDto fileDto = fileService.writeFileInDatabase(file);

        return ResponseEntity.created(uri.path("/api/file/{id}").build(Map.of("id", fileDto.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("response", "the file was recorded", "body", fileDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FileDto> handlerGetFileById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(fileService.getFileById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<PageDto<FileDto>> handlerGetFiles(PageRequestDto pageRequestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(fileService.getFiles(pageRequestDto));
    }
}
