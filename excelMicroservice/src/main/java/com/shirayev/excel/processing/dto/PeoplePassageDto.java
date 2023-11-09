package com.shirayev.excel.processing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Time;

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

}
