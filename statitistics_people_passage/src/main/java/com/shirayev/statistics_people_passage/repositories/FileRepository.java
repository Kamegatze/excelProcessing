package com.shirayev.statistics_people_passage.repositories;

import com.shirayev.statistics_people_passage.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
