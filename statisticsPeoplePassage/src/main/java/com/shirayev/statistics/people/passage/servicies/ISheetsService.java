package com.shirayev.statistics.people.passage.servicies;

import com.shirayev.statistics.people.passage.dto.SheetsDto;
import com.shirayev.statistics.people.passage.entities.File;

import java.util.List;

public interface ISheetsService {

    List<SheetsDto> updateAndInsertOfData(List<SheetsDto> sheetsDtoList, File file);



}
