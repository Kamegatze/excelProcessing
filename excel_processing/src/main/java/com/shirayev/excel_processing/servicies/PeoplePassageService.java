package com.shirayev.excel_processing.servicies;

import com.shirayev.excel_processing.dto.PeoplePassageDto;
import com.shirayev.excel_processing.dto.SheetsDto;
import com.shirayev.excel_processing.entities.PeoplePassage;
import com.shirayev.excel_processing.entities.Sheets;
import com.shirayev.excel_processing.parser.Parser;
import com.shirayev.excel_processing.parser.excel.ExcelParser;
import com.shirayev.excel_processing.repositories.PeoplePassageRepository;
import com.shirayev.excel_processing.repositories.SheetsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PeoplePassageService {

    private final ExcelParser excelParser;

    private final SheetsRepository sheetsRepository;

    private final PeoplePassageRepository peoplePassageRepository;

    private final ModelMapper model;

    @Transactional
    public List<SheetsDto> writeFileInDatabase(InputStream inputStream) throws IOException {

        List<SheetsDto> sheetsDtoList = excelParser.parse(inputStream);

        List<Sheets> sheets = SheetsDto.getSheetsEntity(sheetsDtoList);

        sheets = sheetsRepository.saveAll(sheets);

        sheets.forEach(item -> {

            item.getPeoplePassages().forEach(element -> element.setSheet(item));

            item.setPeoplePassages(peoplePassageRepository.saveAll(item.getPeoplePassages()));

        });

        return SheetsDto.getSheetsDto(sheets);
    }

    public List<PeoplePassageDto> getPeoplePassagesBySheet(Long sheetId) {
        List <PeoplePassage> peoplePassages = sheetsRepository.findById(sheetId)
                .orElseThrow(() -> new NoSuchElementException("Лист с id: " + sheetId + " не был найден"))
                .getPeoplePassages();

        return PeoplePassageDto.getPeoplePassageDto(peoplePassages);
    }

    public PeoplePassageDto getPeoplePassageById(Long id) {
        return model.map(peoplePassageRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Запись с id " + id + " не была найдена")
        ), PeoplePassageDto.class);
    }
}
