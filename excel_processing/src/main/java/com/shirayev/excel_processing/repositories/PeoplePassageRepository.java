package com.shirayev.excel_processing.repositories;

import com.shirayev.excel_processing.entities.PeoplePassage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PeoplePassageRepository extends JpaRepository<PeoplePassage, Long> {

    Optional<Page<PeoplePassage>> findBySheetId(Long sheetId, Pageable pageable);

}
