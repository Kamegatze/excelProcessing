package com.shirayev.statistics_people_passage.dto;

import com.shirayev.statistics_people_passage.entities.StatisticsPeoplePassage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.sql.Time;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StatisticsPeoplePassageDto {

    private Long id;

    private Integer age;

    private String action;

    private Time timeAction;

    private static ModelMapper model = new ModelMapper();

    public static List<StatisticsPeoplePassage> getStatisticsPeoplePassageEntity(List<StatisticsPeoplePassageDto> statisticsPeoplePassageDtoList) {
        return statisticsPeoplePassageDtoList.stream()
                .map(item -> model.map(item, StatisticsPeoplePassage.class))
                .toList();
    }

    public static List<StatisticsPeoplePassageDto> getStatisticsPeoplePassageDto(List<StatisticsPeoplePassage> statisticsPeoplePassages) {
        return statisticsPeoplePassages.stream()
                .map(item -> model.map(item, StatisticsPeoplePassageDto.class))
                .toList();
    }

}