package com.shirayev.statistics_people_passage.servicies;


import com.shirayev.statistics_people_passage.dto.page.PageRequestDto;
import com.shirayev.statistics_people_passage.model.CountPeoplePassageByAction;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatisticsService {

    private final StatisticsPeoplePassageService statisticsPeoplePassageService;

    @Transactional
    public List<CountPeoplePassageByAction> handlerGetStatisticsByActionAndAge(Time start, Time end) {
        return statisticsPeoplePassageService.getStatisticsByActionAndAge(start, end);
    }
}
