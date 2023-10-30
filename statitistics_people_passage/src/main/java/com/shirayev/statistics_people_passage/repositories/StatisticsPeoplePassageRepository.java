package com.shirayev.statistics_people_passage.repositories;

import com.shirayev.statistics_people_passage.entities.StatisticsPeoplePassage;
import com.shirayev.statistics_people_passage.model.AvgAgeGroupByActionStatisticsPeoplePassage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Time;
import java.util.List;

public interface StatisticsPeoplePassageRepository extends JpaRepository<Long, StatisticsPeoplePassage> {

    Page<StatisticsPeoplePassage> findAllByTimeActionBetween(Time start, Time end, Pageable pageable);

}
