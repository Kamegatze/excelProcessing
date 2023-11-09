package com.shirayev.statistics.people.passage.servicies.implementation;


import com.shirayev.statistics.people.passage.servicies.IStatisticsPeoplePassageService;
import com.shirayev.statistics.people.passage.servicies.IStatisticsService;
import com.shirayev.statistics.people.passage.model.CountPeoplePassageByAction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService implements IStatisticsService {

    private final IStatisticsPeoplePassageService statisticsPeoplePassageService;

    @Override
    public List<CountPeoplePassageByAction> handlerGetStatisticsByActionAndAge(Time start, Time end) {
        return statisticsPeoplePassageService.getStatisticsByActionAndAge(start, end);
    }
}
