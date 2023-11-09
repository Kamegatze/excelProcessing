package com.shirayev.excel.processing.client.microservice.uri;



import com.shirayev.excel.processing.dto.page.PageRequestDto;

import java.net.URI;

public interface URIBuilder {

    URI createURI(String path, PageRequestDto pageRequestDto);

    URI createURI(String path);

}
