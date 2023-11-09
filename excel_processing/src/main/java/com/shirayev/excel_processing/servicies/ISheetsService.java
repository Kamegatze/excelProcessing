package com.shirayev.excel_processing.servicies;

import com.shirayev.excel_processing.dto.SheetsResponse;
import com.shirayev.excel_processing.dto.page.PageDto;
import com.shirayev.excel_processing.dto.page.PageRequestDto;

public interface ISheetsService {

    PageDto<SheetsResponse> getSheets(PageRequestDto pageRequestDto);

    SheetsResponse getSheetById(Long id);

    PageDto<SheetsResponse> getSheetsByFileId(Long fileId, PageRequestDto pageRequestDto);

}
