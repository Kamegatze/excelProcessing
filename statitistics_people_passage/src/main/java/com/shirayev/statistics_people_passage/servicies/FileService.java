package com.shirayev.statistics_people_passage.servicies;

import com.shirayev.statistics_people_passage.dto.FileDto;
import com.shirayev.statistics_people_passage.dto.page.PageDto;
import com.shirayev.statistics_people_passage.entities.File;
import com.shirayev.statistics_people_passage.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;

    @Transactional
    public PageDto<FileDto> updateAndInsertOfData(PageDto<FileDto> fileDtoPageDto) {
        List<File> files = FileDto.getFileEntity(fileDtoPageDto.getContent());

        files = fileRepository.saveAll(files);

        fileDtoPageDto.setContent(FileDto.getFileDto(files));

        return fileDtoPageDto;
    }

}
