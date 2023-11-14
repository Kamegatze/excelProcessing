package com.shirayev.statistics.people.passage.servicies.implementation;

import com.shirayev.statistics.people.passage.mapper.Mapper;
import com.shirayev.statistics.people.passage.servicies.IFileService;
import com.shirayev.statistics.people.passage.dto.FileDto;
import com.shirayev.statistics.people.passage.dto.FileNesting;
import com.shirayev.statistics.people.passage.dto.SheetsDto;
import com.shirayev.statistics.people.passage.entities.File;
import com.shirayev.statistics.people.passage.entities.Sheets;
import com.shirayev.statistics.people.passage.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FileService implements IFileService {

    private final FileRepository fileRepository;

    private final ModelMapper model;

    private final SheetsService sheetsService;

    private final StatisticsPeoplePassageService statisticsPeoplePassageService;

    private final Mapper mapperClazz;
    @Override
    public List<FileDto> updateAndInsertOfData(List<FileDto> fileDtoList) {
        List<File> files = mapperClazz.getListObject(fileDtoList, File.class);

        files = fileRepository.saveAllAndFlush(files);

        return mapperClazz.getListObject(files, FileDto.class);
    }
    @Override
    @KafkaListener(topics = "${spring.kafka.topics.statistics.save-file}", groupId = "${spring.kafka.consumer.group-id}")
    public FileDto saveNesting(@Payload FileNesting fileNesting) {

        log.info("Save file with id: {}", fileNesting.getId());

        FileDto fileDto = save(model.map(fileNesting, FileDto.class));

        log.info("Save sheets in file");

        sheetsService.updateAndInsertOfData(mapperClazz.getListObject(fileNesting.getSheets(), SheetsDto.class), model.map(fileDto, File.class));

        log.info("Save statistics_people_passage in sheets");

        fileNesting.getSheets().forEach(sheet -> statisticsPeoplePassageService.updateAndInsertOfData(
                sheet.getPeoplePassages(),
                model.map(sheet, Sheets.class)
        ));

        return fileDto;
    }

    @Override
    public FileDto save(FileDto fileDto) {
        return model.map(fileRepository.save(model.map(fileDto, File.class)), FileDto.class);
    }

}
