package com.shirayev.excel_processing.servicies;

import com.shirayev.excel_processing.dto.SheetsDto;
import com.shirayev.excel_processing.entities.Sheets;
import com.shirayev.excel_processing.parser_of_excel.ExcelParser;
import com.shirayev.excel_processing.repositories.PeoplePassageRepository;
import com.shirayev.excel_processing.repositories.SheetsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PeoplePassageService {

    private final ExcelParser excelParser;

    private final SheetsRepository sheetsRepository;

    private final PeoplePassageRepository peoplePassageRepository;
    @Transactional
    public List<SheetsDto> writeFileInDatabase(InputStream inputStream, Boolean withATitle) throws IOException {
        List<SheetsDto> sheetsDtoList = excelParser.getSheets(inputStream, withATitle);

        List<Sheets> sheets = SheetsDto.getSheetsEntity(sheetsDtoList);

        sheets = sheetsRepository.saveAll(sheets);

        sheets.forEach(item -> {

            item.getPeoplePassages().forEach(element -> element.setSheet(item));

            item.setPeoplePassages(peoplePassageRepository.saveAll(item.getPeoplePassages()));

        });

        return SheetsDto.getSheetsDto(sheets);
    }

}
