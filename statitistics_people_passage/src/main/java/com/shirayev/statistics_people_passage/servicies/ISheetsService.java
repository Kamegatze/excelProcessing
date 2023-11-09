package com.shirayev.statistics_people_passage.servicies;

import com.shirayev.statistics_people_passage.dto.SheetsDto;
import com.shirayev.statistics_people_passage.entities.File;

import java.util.List;

public interface ISheetsService {

    List<SheetsDto> updateAndInsertOfData(List<SheetsDto> sheetsDtoList, File file);



}
