package com.shirayev.excel_processing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SheetsNesting {

    private Long id;

    private String title;

    private List<PeoplePassageDto> peoplePassages;

}
