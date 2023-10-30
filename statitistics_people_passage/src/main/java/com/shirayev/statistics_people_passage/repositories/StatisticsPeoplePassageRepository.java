package com.shirayev.statistics_people_passage.repositories;

import com.shirayev.statistics_people_passage.entities.StatisticsPeoplePassage;
import com.shirayev.statistics_people_passage.model.AvgAgeGroupByActionStatisticsPeoplePassage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Time;
import java.util.List;

public interface StatisticsPeoplePassageRepository extends JpaRepository<Long, StatisticsPeoplePassage> {

    @Query(value = "select actions, avg(age) from statistics_people_passage" +
            "where time_action >= :start and time_action <= :end", nativeQuery = true)
    List<AvgAgeGroupByActionStatisticsPeoplePassage> avgAgeGroupByAction(Time start, Time end);

}
