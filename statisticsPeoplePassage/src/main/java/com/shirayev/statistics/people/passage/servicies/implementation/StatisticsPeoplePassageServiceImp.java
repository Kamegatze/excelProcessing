package com.shirayev.statistics.people.passage.servicies.implementation;

import com.shirayev.statistics.people.passage.mapper.Mapper;
import com.shirayev.statistics.people.passage.dto.StatisticsPeoplePassageDto;
import com.shirayev.statistics.people.passage.entities.Sheets;
import com.shirayev.statistics.people.passage.entities.StatisticsPeoplePassage;
import com.shirayev.statistics.people.passage.model.CountPeoplePassageByAction;
import com.shirayev.statistics.people.passage.repositories.StatisticsPeoplePassageRepository;
import com.shirayev.statistics.people.passage.servicies.StatisticsPeoplePassageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StatisticsPeoplePassageServiceImp implements StatisticsPeoplePassageService {

    private final StatisticsPeoplePassageRepository statisticsPeoplePassageRepository;

    private final Mapper mapperClazz;

    @Override
    public List<StatisticsPeoplePassageDto> updateAndInsertOfData(List<StatisticsPeoplePassageDto> statisticsPeoplePassageDtoList, Sheets sheets) {
        List<StatisticsPeoplePassage> statisticsPeoplePassages = mapperClazz.getListObject(
                statisticsPeoplePassageDtoList,
                StatisticsPeoplePassage.class
        );

        statisticsPeoplePassages.forEach(item -> {
            item.setSheet(sheets);
        });

        statisticsPeoplePassages = statisticsPeoplePassageRepository.saveAll(statisticsPeoplePassages);

        return mapperClazz.getListObject(statisticsPeoplePassages, StatisticsPeoplePassageDto.class);
    }

    @Override
    public List<CountPeoplePassageByAction> getStatisticsByActionAndAge(Time start, Time end) {
        List<StatisticsPeoplePassage> statisticsPeoplePassages = statisticsPeoplePassageRepository.findAllByTimeActionBetween(start, end);

        /*
        * Нахождение количество прошедших людей по действию
        * */
        Map<String, Long> statistics = statisticsPeoplePassages.stream().collect(
                Collectors.groupingBy(
                        StatisticsPeoplePassage::getAction,
                        Collectors.counting()
                )
        );
        /*
        * Формирование ответа метода
        * */
        List<CountPeoplePassageByAction> countPeoplePassageByActions = new ArrayList<>();

        statistics.forEach((key, value) -> {
            countPeoplePassageByActions.add(CountPeoplePassageByAction.builder()
                    .actions(key)
                    .count(value)
                    .build()
            );
        });

        return countPeoplePassageByActions;
    }

}
