package com.shirayev.excel_processing.parser.excel;

import com.shirayev.excel_processing.custom_exception.EmptyValueInRecordException;
import com.shirayev.excel_processing.custom_exception.TitleException;
import com.shirayev.excel_processing.dto.PeoplePassageDto;
import com.shirayev.excel_processing.dto.SheetsDto;
import com.shirayev.excel_processing.parser.Parser;
import lombok.Data;
import lombok.RequiredArgsConstructor;
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
import java.util.function.Function;

@Data
@Component
@Qualifier("excelParser")
public class ExcelParser implements Parser<List<SheetsDto>> {


    private final List<String> fields = List.of("last_name", "first_name", "patronymic",
            "age", "actions", "time_action");

    private List<PeoplePassageDto> getSheet(Sheet sheet) throws TitleException, EmptyValueInRecordException {

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

        List<PeoplePassageDto> result = new ArrayList<>();
        for(Map<String, String> record : records) {
            result.add(checkEmptyValueInRecord(record));
        }

        return result;
    }

    private PeoplePassageDto checkEmptyValueInRecord(Map<String, String> record) throws EmptyValueInRecordException {
        boolean isRecordValid = !(record.get("age").isEmpty() || record.get("age").isBlank())
                && !(record.get("actions").isBlank() || record.get("actions").isEmpty())
                && !(record.get("time_action").isBlank() || record.get("time_action").isEmpty())
                && !(record.get("last_name").isBlank() || record.get("last_name").isEmpty())
                && !(record.get("first_name").isBlank() || record.get("first_name").isEmpty());

        if (!isRecordValid) {
            throw new EmptyValueInRecordException("Проверьте ваши записи на пустые значения. Поля: age, actions, time_actions, last_name, first_name, " +
                    "обязательны к заполнению");
        }

        return PeoplePassageDto.builder()
                .age(Integer.valueOf(record.get("age")))
                .action(record.get("actions"))
                .timeAction(Time.valueOf(record.get("time_action")))
                .last_name(record.get("last_name"))
                .first_name(record.get("first_name"))
                .patronymic(record.get("patronymic"))
                .build();
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
