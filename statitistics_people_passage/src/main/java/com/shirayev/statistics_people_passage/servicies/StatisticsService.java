package com.shirayev.statistics_people_passage.servicies;

import com.shirayev.statistics_people_passage.client_micro_service.ClientMicroService;
import com.shirayev.statistics_people_passage.client_micro_service.uri.URIBuilder;
import com.shirayev.statistics_people_passage.dto.FileDto;
import com.shirayev.statistics_people_passage.dto.FileNesting;
import com.shirayev.statistics_people_passage.dto.SheetsNesting;
import com.shirayev.statistics_people_passage.dto.page.PageDto;
import com.shirayev.statistics_people_passage.dto.page.PageRequestDto;
import com.shirayev.statistics_people_passage.entities.File;
import com.shirayev.statistics_people_passage.entities.Sheets;
import com.shirayev.statistics_people_passage.model.CountPeoplePassageByAction;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatisticsService {

    private final FileService fileService;

    private final SheetsService sheetsService;

    private final StatisticsPeoplePassageService statisticsPeoplePassageService;

    private final ModelMapper model;

    private final URIBuilder uriExcelProcessing;

    private final ClientMicroService<PageDto<FileNesting>> excelProcessingClient;

    @Transactional
    public List<CountPeoplePassageByAction> handlerGetStatisticsByActionAndAge(Time start, Time end, PageRequestDto pageRequestDto) {
        /*
         * Формирование url и получение данных из api
         * Todo Подумать как синхронизировать данные между МС
         * */
        PageDto<FileNesting> loadPage = excelProcessingClient.getData(
                uriExcelProcessing.createURI("/all/nesting", pageRequestDto)
        );

        /*
        * Сохранение данных
        * */
        List<FileDto> fileDtoList = FileNesting.getFileDto(Objects.requireNonNull(loadPage).getContent());

        fileService.updateAndInsertOfData(fileDtoList);

        Objects.requireNonNull(loadPage).getContent().forEach(file -> {
            sheetsService.updateAndInsertOfData(SheetsNesting.getSheetsDto(file.getSheets()), model.map(file, File.class));
            file.getSheets().forEach(sheet -> {
                statisticsPeoplePassageService.updateAndInsertOfData(sheet.getPeoplePassages(), model.map(sheet, Sheets.class));
            });
        });

        /*
        * Формирование ответа
        * */

        return statisticsPeoplePassageService.getStatisticsByActionAndAge(pageRequestDto, start, end);
    }
}
