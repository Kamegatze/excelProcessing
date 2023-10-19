package com.shirayev.excel_processing.dto;

import com.shirayev.excel_processing.entities.PeoplePassage;
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
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PeoplePassageDto {

    private Long id;

    private String last_name;

    private String first_name;

    private String patronymic;

    private Integer age;

    private String action;

    private Time timeAction;

    private static ModelMapper model = new ModelMapper();

    public static List<PeoplePassageDto> getPeoplePassageDto(List<PeoplePassage> peoplePassageList) {
        return peoplePassageList.stream().map(item -> model.map(item, PeoplePassageDto.class)).toList();
    }

    public static List<PeoplePassage> getPeoplePassageEntity(List<PeoplePassageDto> peoplePassageList) {
        return peoplePassageList.stream().map(item -> model.map(item, PeoplePassage.class)).toList();
    }
}
