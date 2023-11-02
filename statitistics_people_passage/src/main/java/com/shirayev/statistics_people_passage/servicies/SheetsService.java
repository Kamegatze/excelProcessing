package com.shirayev.statistics_people_passage.servicies;

import com.shirayev.statistics_people_passage.dto.SheetsDto;
import com.shirayev.statistics_people_passage.entities.File;
import com.shirayev.statistics_people_passage.entities.Sheets;
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

    public List<SheetsDto> updateAndInsertOfData(List<SheetsDto> sheetsDtoList, File file) {
        List<Sheets> sheets = SheetsDto.getSheetsEntity(sheetsDtoList);

        sheets.forEach(item -> {
            item.setFile(file);
        });

        sheets = sheetsRepository.saveAll(sheets);

        return SheetsDto.getSheetsDto(sheets);
    }

}
