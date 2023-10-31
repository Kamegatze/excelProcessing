package com.shirayev.statistics_people_passage.servicies;

import com.shirayev.statistics_people_passage.dto.SheetsDto;
import com.shirayev.statistics_people_passage.dto.page.PageDto;
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

    private SheetsRepository sheetsRepository;

    public PageDto<SheetsDto> updateAndInsertOfData(PageDto<SheetsDto> sheetsDtoPageDto) {
        List<Sheets> sheets = SheetsDto.getSheetsEntity(sheetsDtoPageDto.getContent());

        sheets = sheetsRepository.saveAll(sheets);

        sheetsDtoPageDto.setContent(SheetsDto.getSheetsDto(sheets));

        return sheetsDtoPageDto;
    }

}
