package com.shirayev.excel_processing.parser_of_excel;

import com.shirayev.excel_processing.dto.PeoplePassageDto;
import com.shirayev.excel_processing.dto.SheetsDto;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.*;

@Component
public class ExcelParser {

    private final List<String> fields = List.of("last_name", "first_name", "patronymic",
            "age", "actions", "time_action");

    private List<PeoplePassageDto> getSheet(Sheet sheet, Boolean withATitle) {
        Iterator<Row> rowIterator = sheet.iterator();

        Row row;

        List<String> keys = new ArrayList<>();

        if(withATitle) {

            row = rowIterator.next();

            for (Iterator<Cell> cellIterator = row.cellIterator();
                 cellIterator.hasNext();) {

                Cell cell = cellIterator.next();

                keys.add(cell.getStringCellValue());

            }
        }

        List<Map<String, String>> records = new ArrayList<>();

        while (rowIterator.hasNext()) {
            row = rowIterator.next();


            Map<String, String> record = new HashMap<>();

            int index = 0;

            for(Iterator<Cell> cellIterator = row.cellIterator();
                cellIterator.hasNext();) {

                Cell cell = cellIterator.next();

                if(!cell.getStringCellValue().isBlank() && !cell.getStringCellValue().isEmpty()) {

                    if(withATitle) {
                        record.put(keys.get(index), cell.getStringCellValue());
                    } else {
                        record.put(fields.get(index), cell.getStringCellValue());
                    }

                }

                index++;
            }

            if(!record.isEmpty()) {
                records.add(record);
            }

        }


        /*
        * Обработать случай когда поля будут пустыми
        * */
        return records.stream().map(item ->
            PeoplePassageDto.builder()
                    .age(Integer.valueOf(item.get("age")))
                    .action(item.get("actions"))
                    .timeAction(Time.valueOf(item.get("time_action")))
                    .last_name(item.get("last_name"))
                    .first_name(item.get("first_name"))
                    .patronymic(item.get("patronymic"))
                    .build()
        ).toList();
    }

    public List<SheetsDto> getSheets(InputStream inputStream, Boolean withATitle) throws IOException {
        Workbook book = new HSSFWorkbook(inputStream);

        List<SheetsDto> sheetsDtoList = new ArrayList<>();

        for(Iterator<Sheet> sheetIterator = book.sheetIterator();
            sheetIterator.hasNext();) {
            Sheet sheet = sheetIterator.next();

            sheetsDtoList.add(
                    SheetsDto.builder()
                    .title(sheet.getSheetName())
                    .peoplePassages(this.getSheet(sheet, withATitle))
                    .build()
            );
        }

        return sheetsDtoList;
    }

}
