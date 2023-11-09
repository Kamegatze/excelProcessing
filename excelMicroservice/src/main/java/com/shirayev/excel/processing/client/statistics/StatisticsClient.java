package com.shirayev.excel.processing.client.statistics;

import com.shirayev.excel.processing.client.microservice.TransferDataClient;
import com.shirayev.excel.processing.dto.FileDto;
import com.shirayev.excel.processing.client.microservice.uri.URIBuilder;
import com.shirayev.excel.processing.dto.FileNesting;
import com.shirayev.excel.processing.entities.File;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class StatisticsClient implements IStatisticsClient{

    private final URIBuilder uriStatistics;

    private final TransferDataClient<FileDto> statisticsTransfer;

    private final ModelMapper model;

    @Async
    @Override
    public void handlerTransferData(File file) {

        log.info("Send writing file with id: {} in microservice statistics", file.getId());

        statisticsTransfer.transferData(uriStatistics.createURI("/save"),
                model.map(file, FileNesting.class),
                FileDto.class
        );
    }

}
