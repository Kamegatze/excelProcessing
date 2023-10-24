package com.shirayev.excel_processing.dto;

import com.shirayev.excel_processing.entities.File;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {

    private Long id;

    private String name;

    private static ModelMapper model = new ModelMapper();

    public static List<FileDto> getFileDto(List<File> files) {
        return files.stream().map(item -> model.map(item, FileDto.class)).toList();
    }

    public static List<File> getFileEntity(List<FileDto> fileDtoList) {
        return fileDtoList.stream().map(item -> model.map(item, File.class)).toList();
    }
}
