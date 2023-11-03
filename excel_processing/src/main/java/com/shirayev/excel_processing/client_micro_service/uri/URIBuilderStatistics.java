package com.shirayev.excel_processing.client_micro_service.uri;


import com.shirayev.excel_processing.dto.page.PageRequestDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;


@Component
@ToString
@RequiredArgsConstructor
@Qualifier("uriStatistics")
public class URIBuilderStatistics implements URIBuilder {

    private String protocol;

    private String port;

    private String host;

    private String path;

    private final UriComponentsBuilder uri;

    private final ConfigURIMicroService configURIMicroService;

    @PostConstruct
    private void init() {
        protocol = configURIMicroService.getExcelProcessing().get("protocol");
        port = configURIMicroService.getExcelProcessing().get("port");
        host = configURIMicroService.getExcelProcessing().get("host");
        path = configURIMicroService.getExcelProcessing().get("path");
    }

    @Override
    public URI createURI(String path, PageRequestDto pageRequestDto) {
        return uri.scheme(protocol).host(host).port(port).path(this.path + path)
                .query("pageNumber={pageNumber}&pageSize={pageSize}")
                .build(Map.of("pageNumber", pageRequestDto.getPageNumber(),
                        "pageSize", pageRequestDto.getPageSize()));
    }

    @Override
    public URI createURI(String path) {
        return uri.scheme(protocol).host(host).port(port).path(this.path + path)
                .build().toUri();
    }
}




