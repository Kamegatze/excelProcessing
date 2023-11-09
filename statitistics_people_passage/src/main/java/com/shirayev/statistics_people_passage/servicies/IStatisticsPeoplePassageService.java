package com.shirayev.statistics_people_passage.servicies;

import com.shirayev.statistics_people_passage.dto.StatisticsPeoplePassageDto;
import com.shirayev.statistics_people_passage.entities.Sheets;
import com.shirayev.statistics_people_passage.model.CountPeoplePassageByAction;

import java.sql.Time;
import java.util.List;

public interface IStatisticsPeoplePassageService {

    List<StatisticsPeoplePassageDto> updateAndInsertOfData(List<StatisticsPeoplePassageDto> statisticsPeoplePassageDtoList, Sheets sheets);

    List<CountPeoplePassageByAction> getStatisticsByActionAndAge(Time start, Time end);

}
