package com.shirayev.statistics.people.passage.servicies.implementation;

import com.shirayev.statistics.people.passage.mapper.Mapper;
import com.shirayev.statistics.people.passage.servicies.ISheetsService;
import com.shirayev.statistics.people.passage.dto.SheetsDto;
import com.shirayev.statistics.people.passage.entities.File;
import com.shirayev.statistics.people.passage.entities.Sheets;
import com.shirayev.statistics.people.passage.repositories.SheetsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SheetsService implements ISheetsService {

    private final SheetsRepository sheetsRepository;

    private final Mapper mapperClazz;

    @Override
    public List<SheetsDto> updateAndInsertOfData(List<SheetsDto> sheetsDtoList, File file) {
        List<Sheets> sheets = mapperClazz.getListObject(sheetsDtoList, Sheets.class);

        sheets.forEach(item -> {
            item.setFile(file);
        });

        sheets = sheetsRepository.saveAll(sheets);

        return mapperClazz.getListObject(sheets, SheetsDto.class);
    }

}
