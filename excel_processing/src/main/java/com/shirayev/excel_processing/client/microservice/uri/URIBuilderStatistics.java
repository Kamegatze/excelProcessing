package com.shirayev.excel_processing.client.microservice.uri;


import com.shirayev.excel_processing.dto.page.PageRequestDto;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
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

    private UriComponentsBuilder uri;

    private final ConfigURIMicroService configURIMicroService;

    private final ApplicationContext applicationContext;

    private UriComponentsBuilder createUriComponentBuilder() {
        return this.applicationContext.getBean(UriComponentsBuilder.class);
    }

    @PostConstruct
    private void init() {
        protocol = configURIMicroService.getStatistics().get("protocol");
        port = configURIMicroService.getStatistics().get("port");
        host = configURIMicroService.getStatistics().get("host");
        path = configURIMicroService.getStatistics().get("path");
    }

    @Override
    public URI createURI(String path, PageRequestDto pageRequestDto) {
        this.uri = createUriComponentBuilder();

        return uri.scheme(protocol).host(host).port(port).path(this.path + path)
                .query("pageNumber={pageNumber}&pageSize={pageSize}")
                .build(Map.of("pageNumber", pageRequestDto.getPageNumber(),
                        "pageSize", pageRequestDto.getPageSize()));
    }

    @Override
    public URI createURI(String path) {
        this.uri = createUriComponentBuilder();

        return uri.scheme(protocol).host(host).port(port).path(this.path + path)
                .build().toUri();
    }
}




