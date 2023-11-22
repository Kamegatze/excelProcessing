package com.shirayev.excel.processing.servicies.implementation;

import com.shirayev.excel.processing.dto.SheetsResponse;
import com.shirayev.excel.processing.dto.page.PageDto;
import com.shirayev.excel.processing.dto.page.PageRequestDto;
import com.shirayev.excel.processing.mapper.Mapper;
import com.shirayev.excel.processing.repositories.SheetsRepository;
import com.shirayev.excel.processing.servicies.SheetsService;
import com.shirayev.excel.processing.entities.Sheets;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class SheetsServiceImp implements SheetsService {

    private final SheetsRepository sheetsRepository;

    private final Mapper mapperClazz;

    @Override
    public PageDto<SheetsResponse> getSheets(PageRequestDto pageRequestDto) {
        Page<Sheets> page = sheetsRepository.findAll(PageRequestDto.getPageRequest(pageRequestDto));

        return PageDto.<SheetsResponse>builder()
                .content(mapperClazz.getListObject(page.getContent(), SheetsResponse.class))
                .countPage(page.getTotalPages())
                .countElements(page.getTotalElements())
                .currentPage(pageRequestDto.getPageNumber())
                .countElementsInPage(pageRequestDto.getPageSize())
                .build();
    }

    @Override
    public SheetsResponse getSheetById(Long id) {
        return mapperClazz.getObject(sheetsRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Лист с id: " + id + " не был найден")
        ), SheetsResponse.class);
    }

    @Override
    public PageDto<SheetsResponse> getSheetsByFileId(Long fileId, PageRequestDto pageRequestDto) {
        Page<Sheets> page = sheetsRepository
                .findByFileId(fileId, PageRequestDto.getPageRequest(pageRequestDto))
                .orElseThrow(() -> new NoSuchElementException("Листы с file_id: " + fileId + "не были найдены"));
        return PageDto.<SheetsResponse>builder()
                .content(mapperClazz.getListObject(page.getContent(), SheetsResponse.class))
                .countPage(page.getTotalPages())
                .countElements(page.getTotalElements())
                .currentPage(pageRequestDto.getPageNumber())
                .countElementsInPage(pageRequestDto.getPageSize())
                .build();
    }
}
