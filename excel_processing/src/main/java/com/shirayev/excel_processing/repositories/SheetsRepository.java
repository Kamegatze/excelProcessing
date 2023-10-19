package com.shirayev.excel_processing.repositories;

import com.shirayev.excel_processing.entities.Sheets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SheetsRepository extends JpaRepository<Sheets, Long> {
}
