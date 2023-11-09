package com.shirayev.statistics.people.passage.servicies;

import com.shirayev.statistics.people.passage.model.CountPeoplePassageByAction;

import java.sql.Time;
import java.util.List;

public interface IStatisticsService {

    List<CountPeoplePassageByAction> handlerGetStatisticsByActionAndAge(Time start, Time end);

}
