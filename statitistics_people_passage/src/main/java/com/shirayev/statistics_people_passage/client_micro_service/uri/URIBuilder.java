package com.shirayev.statistics_people_passage.client_micro_service.uri;

import com.shirayev.statistics_people_passage.dto.page.PageRequestDto;

import java.net.URI;

public interface URIBuilder {

    URI createURI(String path, PageRequestDto pageRequestDto);

}
