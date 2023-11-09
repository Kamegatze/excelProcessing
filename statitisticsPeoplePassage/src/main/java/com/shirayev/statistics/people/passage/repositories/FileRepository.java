package com.shirayev.statistics.people.passage.repositories;

import com.shirayev.statistics.people.passage.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
