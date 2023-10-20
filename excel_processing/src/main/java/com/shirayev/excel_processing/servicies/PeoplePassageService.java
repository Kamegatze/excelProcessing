package com.shirayev.excel_processing.servicies;

import com.shirayev.excel_processing.dto.PeoplePassageDto;
import com.shirayev.excel_processing.entities.PeoplePassage;
import com.shirayev.excel_processing.repositories.PeoplePassageRepository;
import com.shirayev.excel_processing.repositories.SheetsRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PeoplePassageService {

    private final SheetsRepository sheetsRepository;

    private final PeoplePassageRepository peoplePassageRepository;

    private final ModelMapper model;

    public List<PeoplePassageDto> getPeoplePassagesBySheet(Long sheetId) {
        List <PeoplePassage> peoplePassages = sheetsRepository.findById(sheetId)
                .orElseThrow(() -> new NoSuchElementException("Лист с id: " + sheetId + " не был найден"))
                .getPeoplePassages();

        return PeoplePassageDto.getPeoplePassageDto(peoplePassages);
    }

    public PeoplePassageDto getPeoplePassageById(Long id) {
        return model.map(peoplePassageRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("Запись с id " + id + " не была найдена")
        ), PeoplePassageDto.class);
    }

    public List<PeoplePassageDto> getPeoplePassages() {
        return PeoplePassageDto.getPeoplePassageDto(peoplePassageRepository.findAll());
    }
}
