package com.shirayev.excel_processing.dto;

import com.shirayev.excel_processing.entities.File;
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
public class FileNesting {

    private Long id;

    private String name;

    private List<SheetsNesting> sheets;

    private static ModelMapper model = new ModelMapper();

    public static List<FileNesting> getFileNesting(List<File> files) {
        return files.stream().map(item -> model.map(item, FileNesting.class)).toList();
    }

    public static List<File> getFileEntity(List<FileNesting> fileNestings) {
        return fileNestings.stream().map(item -> model.map(item, File.class)).toList();
    }

}
