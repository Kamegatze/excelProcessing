package com.shirayev.statistics.people.passage.repositories;

import com.shirayev.statistics.people.passage.entities.StatisticsPeoplePassage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Time;
import java.util.List;

public interface StatisticsPeoplePassageRepository extends JpaRepository<StatisticsPeoplePassage, Long> {

    List<StatisticsPeoplePassage> findAllByTimeActionBetween(Time start, Time end);

}
