package com.shirayev.excel.processing.advice;

import com.shirayev.excel.processing.controllers.FileController;
import com.shirayev.excel.processing.custom.exception.EmptyValueInRecordException;
import com.shirayev.excel.processing.custom.exception.TitleException;
import com.shirayev.excel.processing.dto.error.ETypeError;
import com.shirayev.excel.processing.dto.error.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice(assignableTypes = FileController.class)
public class FileAdvice {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorDto> handlerIOException(IOException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorDto.builder()
                        .code(ETypeError.IO_EXCEPTION.getCode())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(TitleException.class)
    public ResponseEntity<ErrorDto> handlerTitleException(TitleException titleException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorDto.builder()
                        .code(ETypeError.TITLE_EXCEPTION.getCode())
                        .message(titleException.getMessage())
                        .build());
    }

    @ExceptionHandler(EmptyValueInRecordException.class)
    public ResponseEntity<ErrorDto> handlerEmptyValueRecordException(EmptyValueInRecordException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorDto.builder()
                        .code(ETypeError.EMPTY_VALUE_RECORD_EXCEPTION.getCode())
                        .message(exception.getMessage())
                        .build());
    }

}
