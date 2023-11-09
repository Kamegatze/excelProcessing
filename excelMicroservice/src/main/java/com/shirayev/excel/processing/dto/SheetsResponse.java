package com.shirayev.excel.processing.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SheetsResponse {

    private Long id;

    private String title;

    private static ModelMapper model = new ModelMapper();

}
