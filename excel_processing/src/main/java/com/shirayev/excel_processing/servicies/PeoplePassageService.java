package com.shirayev.excel_processing.servicies;

import com.shirayev.excel_processing.dto.PeoplePassageDto;
import com.shirayev.excel_processing.dto.SheetsResponse;
import com.shirayev.excel_processing.dto.page.PageDto;
import com.shirayev.excel_processing.dto.page.PageRequestDto;
import com.shirayev.excel_processing.entities.PeoplePassage;
import com.shirayev.excel_processing.repositories.PeoplePassageRepository;
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
public class PeoplePassageService {

    private final PeoplePassageRepository peoplePassageRepository;

    private final ModelMapper model;

    public PageDto<PeoplePassageDto> getPeoplePassagesBySheet(Long sheetId, PageRequestDto pageRequestDto) {
        Page<PeoplePassage> page = peoplePassageRepository
                .findBySheetId(sheetId, PageRequestDto.getPageRequest(pageRequestDto))
                .orElseThrow(() -> new NoSuchElementException("Записи с sheet_id: " + sheetId + " не были найдены"));
        return PageDto.<PeoplePassageDto>builder()
                .content(PeoplePassageDto.getPeoplePassageDto(page.getContent()))
                .countPage(page.getTotalPages())
                .countElements(page.getTotalElements())
                .currentPage(pageRequestDto.getPageNumber())
                .countElementsInPage(pageRequestDto.getPageSize())
                .build();
    }

    public PeoplePassageDto getPeoplePassageById(Long id) {
        return model.map(peoplePassageRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Запись с id " + id + " не была найдена")
        ), PeoplePassageDto.class);
    }

    public PageDto<PeoplePassageDto> getPeoplePassages(PageRequestDto pageRequestDto) {
        Page<PeoplePassage> page = peoplePassageRepository.findAll(PageRequestDto.getPageRequest(pageRequestDto));

        return PageDto.<PeoplePassageDto>builder()
                .content(PeoplePassageDto.getPeoplePassageDto(page.getContent()))
                .countPage(page.getTotalPages())
                .countElements(page.getTotalElements())
                .currentPage(pageRequestDto.getPageNumber())
                .countElementsInPage(pageRequestDto.getPageSize())
                .build();
    }
}
