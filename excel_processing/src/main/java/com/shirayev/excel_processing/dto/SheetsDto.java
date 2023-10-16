package com.shirayev.excel_processing.dto;

import com.shirayev.excel_processing.entities.Sheets;
import lombok.*;
import org.modelmapper.ModelMapper;

import java.util.List;


@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SheetsDto {

    private String title;

    private List<PeoplePassageDto> peoplePassages;

    private static ModelMapper model = new ModelMapper();

    public static List<Sheets> getSheetsEntity(List<SheetsDto> sheetsDtoList) {
        return sheetsDtoList.stream().map(item -> model.map(item, Sheets.class)).toList();
    }

    public static List<SheetsDto> getSheetsDto(List<Sheets> sheetList) {
        return sheetList.stream().map(item -> model.map(item, SheetsDto.class)).toList();
    }
}
