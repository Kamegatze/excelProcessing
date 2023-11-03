package com.shirayev.excel_processing.client.statistics;

import com.shirayev.excel_processing.client_micro_service.TransferDataClient;
import com.shirayev.excel_processing.client_micro_service.uri.URIBuilder;
import com.shirayev.excel_processing.dto.FileDto;
import com.shirayev.excel_processing.entities.File;
import com.shirayev.excel_processing.repositories.FileRepository;
import com.shirayev.excel_processing.servicies.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@RequiredArgsConstructor
public class StatisticsClient {

    private final URIBuilder uriStatistics;

    private final TransferDataClient<FileDto> statisticsTransfer;

    private FileRepository fileRepository;

    public FileDto handlerTransferData(FileDto fileDto) {

        Long fileId = fileDto.getId();

        File file = fileRepository.findById(fileId)
                .orElseThrow(
                        () -> new NoSuchElementException("Файл с id: " + fileId + " не был найден")
                );

        return statisticsTransfer.transferData(uriStatistics.createURI("/save"),
                file,
                FileDto.class
        );
    }

}
