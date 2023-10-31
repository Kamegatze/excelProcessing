package com.shirayev.statistics_people_passage.dto;


import com.shirayev.statistics_people_passage.entities.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FileDto {

    private Long id;

    private String name;

    private static ModelMapper model = new ModelMapper();

    public static List<File> getFileEntity(List<FileDto> fileDtoList) {
        return fileDtoList.stream()
                .map(item->model.map(item, File.class))
                .toList();
    }

    public static List<FileDto> getFileDto(List<File> files) {
        return files.stream()
                .map(item->model.map(item, FileDto.class))
                .toList();
    }

}
