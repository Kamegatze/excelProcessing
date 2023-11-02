package com.shirayev.statistics_people_passage.servicies;

import com.shirayev.statistics_people_passage.dto.FileDto;
import com.shirayev.statistics_people_passage.dto.FileNesting;
import com.shirayev.statistics_people_passage.dto.SheetsNesting;
import com.shirayev.statistics_people_passage.dto.page.PageDto;
import com.shirayev.statistics_people_passage.dto.page.PageRequestDto;
import com.shirayev.statistics_people_passage.entities.File;
import com.shirayev.statistics_people_passage.entities.Sheets;
import com.shirayev.statistics_people_passage.model.AvgAgeGroupByActionStatisticsPeoplePassage;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatisticsService {

    private final FileService fileService;

    private final SheetsService sheetsService;

    private final StatisticsPeoplePassageService statisticsPeoplePassageService;

    private final RestTemplate restTemplate;

    private final ModelMapper model;

    private final String protocol = "http";

    private final  String port = "8080";

    private final String host = "localhost";

    private final String path = "api/file";

    @Transactional
    public List<AvgAgeGroupByActionStatisticsPeoplePassage> handlerGetStatisticsByActionAndAge(Time start, Time end, PageRequestDto pageRequestDto,
                                                                                               UriComponentsBuilder uri) {
        /*
         * Формирование url и получение данных из api
         * TODO Вынос в отдельную прослойку
         * */
        URI uriLine = uri.scheme(protocol).host(host).port(port).path(path + "/all/nesting")
                .query("pageNumber={pageNumber}&pageSize={pageSize}")
                .build(Map.of("pageNumber", pageRequestDto.getPageNumber(),
                        "pageSize", pageRequestDto.getPageSize()));

        PageDto<FileNesting> loadPage = restTemplate.exchange(uriLine,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PageDto<FileNesting>>(){}).getBody();


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
