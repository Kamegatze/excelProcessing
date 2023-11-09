package com.shirayev.statistics_people_passage.servicies;

import com.shirayev.statistics_people_passage.dto.FileDto;
import com.shirayev.statistics_people_passage.dto.FileNesting;

import java.util.List;

public interface IFileService {

    List<FileDto> updateAndInsertOfData(List<FileDto> fileDtoList);

    FileDto saveNesting(FileNesting fileNesting);

    FileDto save(FileDto fileDto);

}
