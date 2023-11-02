package com.shirayev.statistics_people_passage.client_micro_service.uri;

import com.shirayev.statistics_people_passage.dto.page.PageRequestDto;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;


@Component
@ToString
@RequiredArgsConstructor
@Qualifier("uriExcelProcessing")
public class URIBuilderExcelProcessing implements URIBuilder {

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
}




