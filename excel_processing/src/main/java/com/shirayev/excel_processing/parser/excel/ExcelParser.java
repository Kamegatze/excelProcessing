package com.shirayev.excel_processing.parser.excel;

import com.shirayev.excel_processing.custom_exception.TitleException;
import com.shirayev.excel_processing.dto.PeoplePassageDto;
import com.shirayev.excel_processing.dto.SheetsDto;
import com.shirayev.excel_processing.parser.Parser;
import lombok.Data;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Data
@Component
@Qualifier("excelParser")
public class ExcelParser implements Parser<List<SheetsDto>> {


    private final List<String> fields = List.of("last_name", "first_name", "patronymic",
            "age", "actions", "time_action");

    private List<PeoplePassageDto> getSheet(Sheet sheet) throws TitleException {

        Iterator<Row> rowIterator = sheet.iterator();
        Row row;
        List<String> keys = new ArrayList<>();

        Boolean withATitle = checkTitle(sheet);

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

    private Boolean checkTitle(Sheet sheet) throws TitleException {

        Iterator<Row> rowIterator = sheet.rowIterator();
        Row row = rowIterator.next();
        int countTitle = 0;

        for(Iterator<Cell> cellIterator = row.cellIterator();
            cellIterator.hasNext();) {

            Cell cell = cellIterator.next();
            if(this.fields.contains(cell.getStringCellValue())) {
                countTitle++;
            }

        }

        if(countTitle >= 1 && countTitle < this.fields.size()) {
            throw new TitleException("Заголовок некорректно заполнен");
        }

        return countTitle == this.fields.size();
    }

    private List<SheetsDto> getSheets(InputStream inputStream) throws IOException, TitleException {

        Workbook book = new HSSFWorkbook(inputStream);
        List<SheetsDto> sheetsDtoList = new ArrayList<>();

        for(Iterator<Sheet> sheetIterator = book.sheetIterator();
            sheetIterator.hasNext();) {
            Sheet sheet = sheetIterator.next();
            sheetsDtoList.add(
                    SheetsDto.builder()
                    .title(sheet.getSheetName())
                    .peoplePassages(this.getSheet(sheet))
                    .build()
            );
        }

        return sheetsDtoList;
    }

    @Override
    public List<SheetsDto> parse(InputStream inputStream) throws IOException {
        return this.getSheets(inputStream);
    }
}
