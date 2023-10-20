package com.shirayev.excel_processing.dto;

import com.shirayev.excel_processing.entities.Sheets;
import org.modelmapper.ModelMapper;

import java.util.List;

public class SheetsResponse {

    private Long id;

    private String title;

    private static ModelMapper model = new ModelMapper();

    public static List<SheetsResponse> getSheetsResponse(List<Sheets> sheets) {
        return sheets.stream().map(item -> model.map(item, SheetsResponse.class)).toList();
    }

    public static List<Sheets> getSheetsEntity(List<SheetsResponse> sheetsResponses) {
        return sheetsResponses.stream().map(item -> model.map(item, Sheets.class)).toList();
    }

}
