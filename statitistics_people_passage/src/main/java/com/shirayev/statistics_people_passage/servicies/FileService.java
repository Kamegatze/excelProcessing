package com.shirayev.statistics_people_passage.servicies;

import com.shirayev.statistics_people_passage.dto.FileDto;
import com.shirayev.statistics_people_passage.dto.FileNesting;
import com.shirayev.statistics_people_passage.dto.SheetsDto;
import com.shirayev.statistics_people_passage.dto.SheetsNesting;
import com.shirayev.statistics_people_passage.entities.File;
import com.shirayev.statistics_people_passage.entities.Sheets;
import com.shirayev.statistics_people_passage.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

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
    public FileDto save(FileNesting fileNesting) {
        File file = fileRepository.save(model.map(fileNesting, File.class));

        sheetsService.updateAndInsertOfData(SheetsNesting.getSheetsDto(fileNesting.getSheets()), file);

        fileNesting.getSheets().forEach(sheet -> statisticsPeoplePassageService.updateAndInsertOfData(
                sheet.getPeoplePassages(),
                model.map(sheet, Sheets.class)
        ));

        return model.map(file, FileDto.class);
    }

}
