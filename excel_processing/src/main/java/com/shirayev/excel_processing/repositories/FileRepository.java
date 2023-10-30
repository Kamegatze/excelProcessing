package com.shirayev.excel_processing.repositories;

import com.shirayev.excel_processing.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Long> {

    List<File> findByIdNotIn(List<Integer> ids);

}
