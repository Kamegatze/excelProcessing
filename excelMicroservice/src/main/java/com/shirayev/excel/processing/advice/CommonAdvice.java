package com.shirayev.excel.processing.advice;

import com.shirayev.excel.processing.dto.error.ETypeError;
import com.shirayev.excel.processing.dto.error.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class CommonAdvice {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorDto> handlerNoSuchElementException(NoSuchElementException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ErrorDto.builder()
                        .message(exception.getMessage())
                        .code(ETypeError.NO_SUCH_ELEMENT_EXCEPTION.getCode())
                        .build());
    }

}
