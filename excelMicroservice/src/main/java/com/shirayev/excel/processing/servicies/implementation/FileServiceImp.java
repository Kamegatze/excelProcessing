package com.shirayev.excel.processing.servicies.implementation;

import com.shirayev.excel.processing.dto.FileDto;
import com.shirayev.excel.processing.dto.page.PageDto;
import com.shirayev.excel.processing.dto.page.PageRequestDto;
import com.shirayev.excel.processing.entities.File;
import com.shirayev.excel.processing.mapper.Mapper;
import com.shirayev.excel.processing.repositories.FileRepository;
import com.shirayev.excel.processing.client.statistics.StatisticsClient;
import com.shirayev.excel.processing.dto.FileNesting;
import com.shirayev.excel.processing.dto.SheetsDto;
import com.shirayev.excel.processing.entities.Sheets;
import com.shirayev.excel.processing.parser.Parser;
import com.shirayev.excel.processing.repositories.PeoplePassageRepository;
import com.shirayev.excel.processing.repositories.SheetsRepository;
import com.shirayev.excel.processing.servicies.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FileServiceImp implements FileService {

    private final FileRepository fileRepository;

    private final PeoplePassageRepository peoplePassageRepository;

    private final SheetsRepository sheetsRepository;

    private final Parser<List<SheetsDto>> excelParser;

    private final Mapper mapperClazz;

    private final StatisticsClient statisticsClient;

    @Override
    public FileDto saveFile(MultipartFile multipartFile) throws IOException {

        log.info("Write file in database {}", multipartFile.getOriginalFilename());
        FileDto fileDto = writeFileInDatabase(multipartFile);

        File file = fileRepository.findById(fileDto.getId())
                .orElseThrow(() -> new NoSuchElementException("Файл с id: " + fileDto.getId() + " не был найден"));

        statisticsClient.handlerTransferData(file);

        return fileDto;
    }

    private FileDto writeFileInDatabase(MultipartFile multipartFile) throws IOException {

        List<SheetsDto> sheetsDtoList;

        try(InputStream inputStream = new ByteArrayInputStream(multipartFile.getBytes())) {
            sheetsDtoList = excelParser.parse(inputStream);
        }

        List<Sheets> sheets = mapperClazz.getListObject(sheetsDtoList, Sheets.class);

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

        return mapperClazz.getObject(file, FileDto.class);
    }

    @Override
    public FileDto getFileById(Long id) {
        return mapperClazz.getObject(fileRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Файл с id: " + id + " не был найден")),
                FileDto.class);
    }

    @Override
    public PageDto<FileDto> getFiles(PageRequestDto pageRequestDto) {

        Page<File> pageFile = fileRepository.findAll(PageRequestDto.getPageRequest(pageRequestDto));

        return PageDto.<FileDto>builder()
                .content(mapperClazz.getListObject(pageFile.getContent(), FileDto.class))
                .countPage(pageFile.getTotalPages())
                .countElements(pageFile.getTotalElements())
                .currentPage(pageRequestDto.getPageNumber())
                .countElementsInPage(pageRequestDto.getPageSize())
                .build();

    }

    @Override
    public PageDto<FileNesting> getFilesNesting(PageRequestDto pageRequestDto) {

        Page<File> pageFile = fileRepository.findAll(PageRequestDto.getPageRequest(pageRequestDto));

        return PageDto.<FileNesting>builder()
                .content(mapperClazz.getListObject(pageFile.getContent(), FileNesting.class))
                .countPage(pageFile.getTotalPages())
                .countElements(pageFile.getTotalElements())
                .currentPage(pageRequestDto.getPageNumber())
                .countElementsInPage(pageRequestDto.getPageSize())
                .build();

    }

}
