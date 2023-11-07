package com.shirayev.excel_processing.client_micro_service.uri;



import com.shirayev.excel_processing.dto.page.PageRequestDto;

import java.net.URI;

public interface URIBuilder {

    URI createURI(String path, PageRequestDto pageRequestDto);

    URI createURI(String path);

}
