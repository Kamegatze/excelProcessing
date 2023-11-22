package com.shirayev.excel.processing.servicies;

import com.shirayev.excel.processing.dto.page.PageDto;
import com.shirayev.excel.processing.dto.page.PageRequestDto;
import com.shirayev.excel.processing.dto.PeoplePassageDto;

public interface PeoplePassageService {

    PageDto<PeoplePassageDto> getPeoplePassagesBySheet(Long sheetId, PageRequestDto pageRequestDto);

    PeoplePassageDto getPeoplePassageById(Long id);

    PageDto<PeoplePassageDto> getPeoplePassages(PageRequestDto pageRequestDto);

}
