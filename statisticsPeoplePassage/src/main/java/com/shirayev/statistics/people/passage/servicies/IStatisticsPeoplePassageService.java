package com.shirayev.statistics.people.passage.servicies;

import com.shirayev.statistics.people.passage.dto.StatisticsPeoplePassageDto;
import com.shirayev.statistics.people.passage.entities.Sheets;
import com.shirayev.statistics.people.passage.model.CountPeoplePassageByAction;

import java.sql.Time;
import java.util.List;

public interface IStatisticsPeoplePassageService {

    List<StatisticsPeoplePassageDto> updateAndInsertOfData(List<StatisticsPeoplePassageDto> statisticsPeoplePassageDtoList, Sheets sheets);

    List<CountPeoplePassageByAction> getStatisticsByActionAndAge(Time start, Time end);

}
