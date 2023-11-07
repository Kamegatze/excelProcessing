package com.shirayev.statistics_people_passage.repositories;

import com.shirayev.statistics_people_passage.entities.StatisticsPeoplePassage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Time;
import java.util.List;

public interface StatisticsPeoplePassageRepository extends JpaRepository<StatisticsPeoplePassage, Long> {

    List<StatisticsPeoplePassage> findAllByTimeActionBetween(Time start, Time end);

}
