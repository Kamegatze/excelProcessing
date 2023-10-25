package com.shirayev.excel_processing.repositories;

import com.shirayev.excel_processing.entities.Sheets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SheetsRepository extends JpaRepository<Sheets, Long> {

    Page<Sheets> findByFileId(Long fileId, Pageable pageable);

}
