package com.shirayev.statistics_people_passage.servicies;

import com.shirayev.statistics_people_passage.dto.StatisticsPeoplePassageDto;
import com.shirayev.statistics_people_passage.dto.page.PageRequestDto;
import com.shirayev.statistics_people_passage.entities.Sheets;
import com.shirayev.statistics_people_passage.entities.StatisticsPeoplePassage;
import com.shirayev.statistics_people_passage.model.AvgAgeGroupByActionStatisticsPeoplePassage;
import com.shirayev.statistics_people_passage.repositories.StatisticsPeoplePassageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
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

    public List<AvgAgeGroupByActionStatisticsPeoplePassage> getStatisticsByActionAndAge(PageRequestDto pageRequestDto, Time start, Time end) {
        Page<StatisticsPeoplePassage> pageStatisticsPeoplePassage = statisticsPeoplePassageRepository.findAllByTimeActionBetween(start, end,
                PageRequestDto.getPageRequest(pageRequestDto));

        List<StatisticsPeoplePassage> statisticsPeoplePassages = pageStatisticsPeoplePassage.getContent();

        /*
        * Нахождение среднего возраста по действию
        * */
        Map<String, Double> statistics = statisticsPeoplePassages.stream().collect(
                Collectors.groupingBy(
                        StatisticsPeoplePassage::getAction,
                        Collectors.averagingInt(StatisticsPeoplePassage::getAge
                        )
                )
        );
        /*
        * Формирование отсвета метода
        * */
        List<AvgAgeGroupByActionStatisticsPeoplePassage> avgAgeGroupByActionStatisticsPeoplePassages = new ArrayList<>();

        statistics.forEach((key, value) -> {
            avgAgeGroupByActionStatisticsPeoplePassages.add(AvgAgeGroupByActionStatisticsPeoplePassage.builder()
                    .actions(key)
                    .age(value)
                    .build()
            );
        });

        return avgAgeGroupByActionStatisticsPeoplePassages;
    }

}
