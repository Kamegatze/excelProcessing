package com.shirayev.excel_processing.servicies;

import com.shirayev.excel_processing.dto.SheetsResponse;
import com.shirayev.excel_processing.entities.Sheets;
import com.shirayev.excel_processing.repositories.FileRepository;
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

    private final FileRepository fileRepository;

    public List<SheetsResponse> getSheets() {
        return SheetsResponse.getSheetsResponse(sheetsRepository.findAll());
    }

    public SheetsResponse getSheetById(Long id) {
        return model.map(sheetsRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Лист с id: " + id + " не был найден")
        ), SheetsResponse.class);
    }

    public List<SheetsResponse> getSheetsByFileId(Long fileId) {
        List<Sheets> sheets = fileRepository.findById(fileId)
                .orElseThrow(() -> new NoSuchElementException("Файл с id: " + fileId + " не был найден"))
                .getSheets();

        return SheetsResponse.getSheetsResponse(sheets);
    }
}
