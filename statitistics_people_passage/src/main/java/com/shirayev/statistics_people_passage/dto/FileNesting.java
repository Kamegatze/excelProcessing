package com.shirayev.statistics_people_passage.dto;

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

    public static List<FileDto> getFileDto(List<FileNesting> fileNestings) {
        return fileNestings.stream().map(item->model.map(item, FileDto.class)).toList();
    }

}
