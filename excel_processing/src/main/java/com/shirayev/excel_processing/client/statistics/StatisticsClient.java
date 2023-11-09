package com.shirayev.excel_processing.client.statistics;

import com.shirayev.excel_processing.client_micro_service.TransferDataClient;
import com.shirayev.excel_processing.client_micro_service.uri.URIBuilder;
import com.shirayev.excel_processing.dto.FileDto;
import com.shirayev.excel_processing.dto.FileNesting;
import com.shirayev.excel_processing.entities.File;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class StatisticsClient {

    private final URIBuilder uriStatistics;

    private final TransferDataClient<FileDto> statisticsTransfer;

    private final ModelMapper model;

    private final static Logger LOGGER = LoggerFactory.getLogger(StatisticsClient.class);

    @Async
    public void handlerTransferData(File file) {

        LOGGER.info("Send writing file with id: {} in microservice statistics", file.getId());

        statisticsTransfer.transferData(uriStatistics.createURI("/save"),
                model.map(file, FileNesting.class),
                FileDto.class
        );
    }

}
