package com.shirayev.statistics_people_passage.servicies;

import com.shirayev.statistics_people_passage.dto.FileDto;
import com.shirayev.statistics_people_passage.dto.FileNesting;
import com.shirayev.statistics_people_passage.dto.SheetsNesting;
import com.shirayev.statistics_people_passage.entities.File;
import com.shirayev.statistics_people_passage.entities.Sheets;
import com.shirayev.statistics_people_passage.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

    private final FileRepository fileRepository;

    private final ModelMapper model;

    private final SheetsService sheetsService;

    private final StatisticsPeoplePassageService statisticsPeoplePassageService;

    @Transactional
    public List<FileDto> updateAndInsertOfData(List<FileDto> fileDtoList) {
        List<File> files = FileDto.getFileEntity(fileDtoList);

        files = fileRepository.saveAllAndFlush(files);

        return FileDto.getFileDto(files);
    }

    @Transactional
    public FileDto saveNesting(FileNesting fileNesting) {

        LOGGER.info("Save file with id: {}", fileNesting.getId());

        FileDto fileDto = save(model.map(fileNesting, FileDto.class));

        LOGGER.info("Save sheets in file");

        sheetsService.updateAndInsertOfData(SheetsNesting.getSheetsDto(fileNesting.getSheets()), model.map(fileDto, File.class));

        LOGGER.info("Save statistics_people_passage in sheets");

        fileNesting.getSheets().forEach(sheet -> statisticsPeoplePassageService.updateAndInsertOfData(
                sheet.getPeoplePassages(),
                model.map(sheet, Sheets.class)
        ));

        return fileDto;
    }

    @Transactional
    public FileDto save(FileDto fileDto) {
        return model.map(fileRepository.save(model.map(fileDto, File.class)), FileDto.class);
    }

}
