package com.shirayev.statistics_people_passage.servicies;

import com.shirayev.statistics_people_passage.dto.SheetsDto;
import com.shirayev.statistics_people_passage.entities.File;
import com.shirayev.statistics_people_passage.entities.Sheets;
import com.shirayev.statistics_people_passage.mapper.Mapper;
import com.shirayev.statistics_people_passage.repositories.SheetsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SheetsService {

    private final SheetsRepository sheetsRepository;

    private final Mapper mapperClazz;

    public List<SheetsDto> updateAndInsertOfData(List<SheetsDto> sheetsDtoList, File file) {
        List<Sheets> sheets = mapperClazz.getListObject(sheetsDtoList, Sheets.class);

        sheets.forEach(item -> {
            item.setFile(file);
        });

        sheets = sheetsRepository.saveAll(sheets);

        return mapperClazz.getListObject(sheets, SheetsDto.class);
    }

}
