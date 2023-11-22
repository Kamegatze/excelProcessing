package com.shirayev.excel.processing.controllers;


import com.shirayev.excel.processing.dto.FileDto;
import com.shirayev.excel.processing.dto.page.PageRequestDto;
import com.shirayev.excel.processing.dto.FileNesting;
import com.shirayev.excel.processing.dto.page.PageDto;
import com.shirayev.excel.processing.servicies.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/file")
public class FileController {

    private final FileService fileService;

    @PostMapping("/write_file")
    public ResponseEntity<FileDto> handlerWriteFileInDatabase(@RequestParam MultipartFile file, UriComponentsBuilder uri)
            throws IOException {

        FileDto fileDto = fileService.saveFile(file);

        return ResponseEntity.created(uri.path("/api/file/{id}").build(Map.of("id", fileDto.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(fileDto);
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

    @GetMapping("/all/nesting")
    public ResponseEntity<PageDto<FileNesting>> handlerGetFilesNesting(PageRequestDto pageRequestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(fileService.getFilesNesting(pageRequestDto));
    }
}
