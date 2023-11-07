package com.shirayev.excel_processing.servicies;

import com.shirayev.excel_processing.dto.SheetsResponse;
import com.shirayev.excel_processing.dto.page.PageDto;
import com.shirayev.excel_processing.dto.page.PageRequestDto;
import com.shirayev.excel_processing.entities.Sheets;
import com.shirayev.excel_processing.repositories.SheetsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
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

    public PageDto<SheetsResponse> getSheets(PageRequestDto pageRequestDto) {
        Page<Sheets> page = sheetsRepository.findAll(PageRequestDto.getPageRequest(pageRequestDto));

        return PageDto.<SheetsResponse>builder()
                .content(SheetsResponse.getSheetsResponse(page.getContent()))
                .countPage(page.getTotalPages())
                .countElements(page.getTotalElements())
                .currentPage(pageRequestDto.getPageNumber())
                .countElementsInPage(pageRequestDto.getPageSize())
                .build();
    }

    public SheetsResponse getSheetById(Long id) {
        return model.map(sheetsRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Лист с id: " + id + " не был найден")
        ), SheetsResponse.class);
    }

    public PageDto<SheetsResponse> getSheetsByFileId(Long fileId, PageRequestDto pageRequestDto) {
        Page<Sheets> page = sheetsRepository
                .findByFileId(fileId, PageRequestDto.getPageRequest(pageRequestDto))
                .orElseThrow(() -> new NoSuchElementException("Листы с file_id: " + fileId + "не были найдены"));
        return PageDto.<SheetsResponse>builder()
                .content(SheetsResponse.getSheetsResponse(page.getContent()))
                .countPage(page.getTotalPages())
                .countElements(page.getTotalElements())
                .currentPage(pageRequestDto.getPageNumber())
                .countElementsInPage(pageRequestDto.getPageSize())
                .build();
    }
}
