package com.shirayev.statistics_people_passage.dto;

import com.shirayev.statistics_people_passage.entities.Sheets;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SheetsDto {

    private Long id;

    private String title;

    public static ModelMapper model = new ModelMapper();

    public static List<Sheets> getSheetsEntity(List<SheetsDto> sheetsDtoList) {
        return sheetsDtoList.stream()
                .map(item->model.map(item, Sheets.class))
                .toList();
    }

    public static List<SheetsDto> getSheetsDto(List<Sheets> sheets) {
        return sheets.stream()
                .map(item->model.map(item, SheetsDto.class))
                .toList();
    }
}
