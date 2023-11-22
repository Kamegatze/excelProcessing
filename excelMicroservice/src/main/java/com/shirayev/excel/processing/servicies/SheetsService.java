package com.shirayev.excel.processing.servicies;

import com.shirayev.excel.processing.dto.SheetsResponse;
import com.shirayev.excel.processing.dto.page.PageDto;
import com.shirayev.excel.processing.dto.page.PageRequestDto;

public interface SheetsService {

    PageDto<SheetsResponse> getSheets(PageRequestDto pageRequestDto);

    SheetsResponse getSheetById(Long id);

    PageDto<SheetsResponse> getSheetsByFileId(Long fileId, PageRequestDto pageRequestDto);

}
