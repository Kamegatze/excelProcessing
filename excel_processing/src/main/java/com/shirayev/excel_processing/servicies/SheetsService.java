package com.shirayev.excel_processing.servicies;

import com.shirayev.excel_processing.dto.SheetsDto;
import com.shirayev.excel_processing.repositories.SheetsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SheetsService {

    private final SheetsRepository sheetsRepository;

    private final ModelMapper model;

    public List<SheetsDto> getSheets() {
        return SheetsDto.getSheetsDto(sheetsRepository.findAll());
    }

    public SheetsDto getSheetById(Long id) {
        return model.map(sheetsRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Лист с id: " + id + " не был найден")
        ), SheetsDto.class);
    }
}
