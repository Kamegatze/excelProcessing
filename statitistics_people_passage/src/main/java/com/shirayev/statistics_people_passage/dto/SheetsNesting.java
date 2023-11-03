package com.shirayev.statistics_people_passage.dto;

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
public class SheetsNesting {

    private Long id;

    private String title;

    private List<StatisticsPeoplePassageDto> peoplePassages;

    private static ModelMapper model = new ModelMapper();

    public static List<SheetsDto> getSheetsDto(List<SheetsNesting> sheetsNestings) {
        return sheetsNestings.stream().map(item->model.map(item, SheetsDto.class)).toList();
    }
}
