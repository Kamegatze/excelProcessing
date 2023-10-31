package com.shirayev.statistics_people_passage.servicies;

import com.shirayev.statistics_people_passage.dto.page.PageDto;
import com.shirayev.statistics_people_passage.dto.StatisticsPeoplePassageDto;
import com.shirayev.statistics_people_passage.entities.StatisticsPeoplePassage;
import com.shirayev.statistics_people_passage.repositories.StatisticsPeoplePassageRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatisticsPeoplePassageService {

    private final StatisticsPeoplePassageRepository statisticsPeoplePassageRepository;

    private final ModelMapper modelMapper;

    @Transactional
    public PageDto<StatisticsPeoplePassageDto> updateAndInsertOfData(PageDto<StatisticsPeoplePassageDto> statisticsPeoplePassageDtoPageDto) {
        List<StatisticsPeoplePassage> statisticsPeoplePassages = StatisticsPeoplePassageDto
                .getStatisticsPeoplePassageEntity(statisticsPeoplePassageDtoPageDto.getContent());

        statisticsPeoplePassages = statisticsPeoplePassageRepository.saveAll(statisticsPeoplePassages);

        statisticsPeoplePassageDtoPageDto.setContent(StatisticsPeoplePassageDto.getStatisticsPeoplePassageDto(statisticsPeoplePassages));

        return statisticsPeoplePassageDtoPageDto;
    }

}
