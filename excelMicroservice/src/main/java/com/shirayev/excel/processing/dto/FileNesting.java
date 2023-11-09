package com.shirayev.excel.processing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

}
