package com.shirayev.statistics_people_passage.servicies.implementation;

import com.shirayev.statistics_people_passage.dto.FileDto;
import com.shirayev.statistics_people_passage.dto.FileNesting;
import com.shirayev.statistics_people_passage.dto.SheetsDto;
import com.shirayev.statistics_people_passage.entities.File;
import com.shirayev.statistics_people_passage.entities.Sheets;
import com.shirayev.statistics_people_passage.mapper.Mapper;
import com.shirayev.statistics_people_passage.repositories.FileRepository;
import com.shirayev.statistics_people_passage.servicies.IFileService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FileService implements IFileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

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
    public FileDto saveNesting(FileNesting fileNesting) {

        LOGGER.info("Save file with id: {}", fileNesting.getId());

        FileDto fileDto = save(model.map(fileNesting, FileDto.class));

        LOGGER.info("Save sheets in file");

        sheetsService.updateAndInsertOfData(mapperClazz.getListObject(fileNesting.getSheets(), SheetsDto.class), model.map(fileDto, File.class));

        LOGGER.info("Save statistics_people_passage in sheets");

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
