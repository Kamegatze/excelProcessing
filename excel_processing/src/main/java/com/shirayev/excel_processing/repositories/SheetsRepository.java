package com.shirayev.excel_processing.repositories;

import com.shirayev.excel_processing.entities.Sheets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SheetsRepository extends JpaRepository<Sheets, Long> {

    Optional<Page<Sheets>> findByFileId(Long fileId, Pageable pageable);

}
