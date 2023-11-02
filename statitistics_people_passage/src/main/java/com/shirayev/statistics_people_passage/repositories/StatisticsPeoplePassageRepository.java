package com.shirayev.statistics_people_passage.repositories;

import com.shirayev.statistics_people_passage.entities.StatisticsPeoplePassage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Time;

public interface StatisticsPeoplePassageRepository extends JpaRepository<StatisticsPeoplePassage, Long> {

    Page<StatisticsPeoplePassage> findAllByTimeActionBetween(Time start, Time end, Pageable pageable);

}
