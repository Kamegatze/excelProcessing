package com.shirayev.excel_processing.servicies;

import com.shirayev.excel_processing.client.statistics.StatisticsClient;
import com.shirayev.excel_processing.dto.FileDto;
import com.shirayev.excel_processing.dto.FileNesting;
import com.shirayev.excel_processing.dto.SheetsDto;
import com.shirayev.excel_processing.dto.page.PageDto;
import com.shirayev.excel_processing.dto.page.PageRequestDto;
import com.shirayev.excel_processing.entities.File;
import com.shirayev.excel_processing.entities.Sheets;
import com.shirayev.excel_processing.parser.Parser;
import com.shirayev.excel_processing.repositories.FileRepository;
import com.shirayev.excel_processing.repositories.PeoplePassageRepository;
import com.shirayev.excel_processing.repositories.SheetsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileService.class);

    private final FileRepository fileRepository;

    private final PeoplePassageRepository peoplePassageRepository;

    private final SheetsRepository sheetsRepository;

    private final Parser<List<SheetsDto>> excelParser;

    private final ModelMapper model;

    private final StatisticsClient statisticsClient;

    @Async
    @Transactional
    public CompletableFuture<FileDto> saveFile(MultipartFile multipartFile) throws IOException {

        LOGGER.info("Write file in database {}", multipartFile.getOriginalFilename());
        FileDto fileDto = writeFileInDatabase(multipartFile);

        LOGGER.info("Send writing file with id: {} in microservice statistics", fileDto.getId());
        return CompletableFuture.completedFuture(statisticsClient.handlerTransferData(fileDto));
    }

    private FileDto writeFileInDatabase(MultipartFile multipartFile) throws IOException {

        List<SheetsDto> sheetsDtoList;

        try(InputStream inputStream = new ByteArrayInputStream(multipartFile.getBytes())) {
            sheetsDtoList = excelParser.parse(inputStream);
        }

        List<Sheets> sheets = SheetsDto.getSheetsEntity(sheetsDtoList);

        File file = File.builder()
                .name(multipartFile.getOriginalFilename())
                .sheets(sheets)
                .build();

        file = fileRepository.save(file);

        File saveFile = file;

        sheets.forEach(item -> {
            item.setFile(saveFile);
        });

        sheets = sheetsRepository.saveAll(sheets);

        sheets.forEach(item -> {

            item.getPeoplePassages().forEach(element -> element.setSheet(item));

            item.setPeoplePassages(peoplePassageRepository.saveAll(item.getPeoplePassages()));

        });

        return model.map(file, FileDto.class);
    }

    public FileDto getFileById(Long id) {
        return model.map(fileRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Файл с id: " + id + " не был найден")),
                FileDto.class);
    }

    public PageDto<FileDto> getFiles(PageRequestDto pageRequestDto) {

        Page<File> pageFile = fileRepository.findAll(PageRequestDto.getPageRequest(pageRequestDto));

        return PageDto.<FileDto>builder()
                .content(FileDto.getFileDto(pageFile.getContent()))
                .countPage(pageFile.getTotalPages())
                .countElements(pageFile.getTotalElements())
                .currentPage(pageRequestDto.getPageNumber())
                .countElementsInPage(pageRequestDto.getPageSize())
                .build();

    }

    public PageDto<FileNesting> getFilesNesting(PageRequestDto pageRequestDto) {

        Page<File> pageFile = fileRepository.findAll(PageRequestDto.getPageRequest(pageRequestDto));

        return PageDto.<FileNesting>builder()
                .content(FileNesting.getFileNesting(pageFile.getContent()))
                .countPage(pageFile.getTotalPages())
                .countElements(pageFile.getTotalElements())
                .currentPage(pageRequestDto.getPageNumber())
                .countElementsInPage(pageRequestDto.getPageSize())
                .build();

    }

}
