package com.shirayev.excel.processing.repositories;

import com.shirayev.excel.processing.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {

}
