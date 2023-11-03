package com.shirayev.excel_processing.client.statistics;

import com.shirayev.excel_processing.client_micro_service.TransferDataClient;
import com.shirayev.excel_processing.client_micro_service.uri.URIBuilder;
import com.shirayev.excel_processing.dto.FileDto;
import com.shirayev.excel_processing.entities.File;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatisticsClient {

    private final URIBuilder uriStatistics;

    private final TransferDataClient<FileDto> statisticsTransfer;

    public FileDto handlerTransferData(File file) {
        return statisticsTransfer.transferData(uriStatistics.createURI("/save"),
                file,
                FileDto.class
        );
    }

}
