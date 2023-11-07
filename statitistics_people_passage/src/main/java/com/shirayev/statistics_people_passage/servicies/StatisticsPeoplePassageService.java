package com.shirayev.statistics_people_passage.servicies;

import com.shirayev.statistics_people_passage.dto.StatisticsPeoplePassageDto;
import com.shirayev.statistics_people_passage.entities.Sheets;
import com.shirayev.statistics_people_passage.entities.StatisticsPeoplePassage;
import com.shirayev.statistics_people_passage.model.CountPeoplePassageByAction;
import com.shirayev.statistics_people_passage.repositories.StatisticsPeoplePassageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatisticsPeoplePassageService {

    private final StatisticsPeoplePassageRepository statisticsPeoplePassageRepository;

    private final ModelMapper model;
    @Transactional
    public List<StatisticsPeoplePassageDto> updateAndInsertOfData(List<StatisticsPeoplePassageDto> statisticsPeoplePassageDtoList, Sheets sheets) {
        List<StatisticsPeoplePassage> statisticsPeoplePassages = StatisticsPeoplePassageDto
                .getStatisticsPeoplePassageEntity(statisticsPeoplePassageDtoList);

        statisticsPeoplePassages.forEach(item -> {
            item.setSheet(sheets);
        });

        statisticsPeoplePassages = statisticsPeoplePassageRepository.saveAll(statisticsPeoplePassages);

        return StatisticsPeoplePassageDto.getStatisticsPeoplePassageDto(statisticsPeoplePassages);
    }

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
