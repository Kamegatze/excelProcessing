package com.shirayev.statistics.people.passage.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Time;

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

}