package com.shirayev.excel_processing.servicies;

import com.shirayev.excel_processing.dto.PeoplePassageDto;
import com.shirayev.excel_processing.dto.page.PageDto;
import com.shirayev.excel_processing.dto.page.PageRequestDto;

public interface IPeoplePassageService {

    PageDto<PeoplePassageDto> getPeoplePassagesBySheet(Long sheetId, PageRequestDto pageRequestDto);

    PeoplePassageDto getPeoplePassageById(Long id);

    PageDto<PeoplePassageDto> getPeoplePassages(PageRequestDto pageRequestDto);

}
