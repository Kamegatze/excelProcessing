package com.shirayev.statistics_people_passage.servicies.implementation;


import com.shirayev.statistics_people_passage.model.CountPeoplePassageByAction;
import com.shirayev.statistics_people_passage.servicies.IStatisticsPeoplePassageService;
import com.shirayev.statistics_people_passage.servicies.IStatisticsService;
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
