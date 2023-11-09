package com.shirayev.excel.processing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;


@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SheetsDto {

    private Long id;

    private String title;

    private List<PeoplePassageDto> peoplePassages;

}
