package com.shirayev.excel_processing.servicies;

import com.shirayev.excel_processing.dto.FileDto;
import com.shirayev.excel_processing.dto.SheetsDto;
import com.shirayev.excel_processing.entities.File;
import com.shirayev.excel_processing.entities.Sheets;
import com.shirayev.excel_processing.parser.Parser;
import com.shirayev.excel_processing.repositories.FileRepository;
import com.shirayev.excel_processing.repositories.PeoplePassageRepository;
import com.shirayev.excel_processing.repositories.SheetsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    private final PeoplePassageRepository peoplePassageRepository;

    private final SheetsRepository sheetsRepository;

    private final Parser<List<SheetsDto>> excelParser;

    private final ModelMapper model;
    @Transactional
    public FileDto writeFileInDatabase(MultipartFile multipartFile) throws IOException {
        InputStream inputStream = new ByteArrayInputStream(multipartFile.getBytes());
        List<SheetsDto> sheetsDtoList = excelParser.parse(inputStream);
        inputStream.close();

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

    public List<FileDto> getFiles() {
        return FileDto.getFileDto(fileRepository.findAll());
    }

}
