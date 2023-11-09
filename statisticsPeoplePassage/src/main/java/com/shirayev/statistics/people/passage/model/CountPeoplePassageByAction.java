package com.shirayev.statistics.people.passage.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CountPeoplePassageByAction {

    private String actions;

    private Long count;

}
