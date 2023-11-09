package com.shirayev.statistics_people_passage.servicies;

import com.shirayev.statistics_people_passage.model.CountPeoplePassageByAction;

import java.sql.Time;
import java.util.List;

public interface IStatisticsService {

    List<CountPeoplePassageByAction> handlerGetStatisticsByActionAndAge(Time start, Time end);

}
