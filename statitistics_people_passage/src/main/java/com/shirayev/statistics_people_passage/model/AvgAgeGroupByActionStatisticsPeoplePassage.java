package com.shirayev.statistics_people_passage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AvgAgeGroupByActionStatisticsPeoplePassage {

    private String actions;

    private Double age;

}
