package com.shirayev.excel_processing.servicies.implementation;

import com.shirayev.excel_processing.dto.PeoplePassageDto;
import com.shirayev.excel_processing.dto.page.PageDto;
import com.shirayev.excel_processing.dto.page.PageRequestDto;
import com.shirayev.excel_processing.entities.PeoplePassage;
import com.shirayev.excel_processing.mapper.Mapper;
import com.shirayev.excel_processing.repositories.PeoplePassageRepository;
import com.shirayev.excel_processing.servicies.IPeoplePassageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class PeoplePassageService implements IPeoplePassageService {

    private final PeoplePassageRepository peoplePassageRepository;

    private final Mapper mapperClazz;

    @Override
    public PageDto<PeoplePassageDto> getPeoplePassagesBySheet(Long sheetId, PageRequestDto pageRequestDto) {
        Page<PeoplePassage> page = peoplePassageRepository
                .findBySheetId(sheetId, PageRequestDto.getPageRequest(pageRequestDto))
                .orElseThrow(() -> new NoSuchElementException("Записи с sheet_id: " + sheetId + " не были найдены"));
        return PageDto.<PeoplePassageDto>builder()
                .content(mapperClazz.getListObject(page.getContent(), PeoplePassageDto.class))
                .countPage(page.getTotalPages())
                .countElements(page.getTotalElements())
                .currentPage(pageRequestDto.getPageNumber())
                .countElementsInPage(pageRequestDto.getPageSize())
                .build();
    }

    @Override
    public PeoplePassageDto getPeoplePassageById(Long id) {
        return mapperClazz.getObject(peoplePassageRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Запись с id " + id + " не была найдена")
        ), PeoplePassageDto.class);
    }

    @Override
    public PageDto<PeoplePassageDto> getPeoplePassages(PageRequestDto pageRequestDto) {
        Page<PeoplePassage> page = peoplePassageRepository.findAll(PageRequestDto.getPageRequest(pageRequestDto));

        return PageDto.<PeoplePassageDto>builder()
                .content(mapperClazz.getListObject(page.getContent(), PeoplePassageDto.class))
                .countPage(page.getTotalPages())
                .countElements(page.getTotalElements())
                .currentPage(pageRequestDto.getPageNumber())
                .countElementsInPage(pageRequestDto.getPageSize())
                .build();
    }
}
