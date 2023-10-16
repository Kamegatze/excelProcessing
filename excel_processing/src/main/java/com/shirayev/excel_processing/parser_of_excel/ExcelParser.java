package com.shirayev.excel_processing.parser_of_excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Component
public class ExcelParser {

    private List<Map<String, String>> getSheet(Sheet sheet, Boolean withATitle) {
        Iterator<Row> rowIterator = sheet.iterator();

        Row row = rowIterator.next();

        List<String> keys = new ArrayList<>();

        if(withATitle) {

            for (Iterator<Cell> cellIterator = row.cellIterator();
                 cellIterator.hasNext();) {

                Cell cell = cellIterator.next();

                keys.add(cell.getStringCellValue());

            }
        }

        List<Map<String, String>> records = new ArrayList<>();

        while (rowIterator.hasNext()) {
            row = rowIterator.next();

            Map<String, String> record = new LinkedHashMap<>();

            int index = 0;

            for(Iterator<Cell> cellIterator = row.cellIterator();
                cellIterator.hasNext();) {

                Cell cell = cellIterator.next();

                if(withATitle) {
                    record.put(keys.get(index), cell.getStringCellValue());
                } else {
                    record.put(String.valueOf(index), cell.getStringCellValue());
                }

                index++;
            }

            records.add(record);

        }

        return records;
    }

    public Map<String, List<Map<String, String>>> getSheets(InputStream inputStream, Boolean withATitle) throws IOException {
        Workbook book = new HSSFWorkbook(inputStream);

        Map<String, List<Map<String, String>>> sheets = new HashMap<>();

        for(Iterator<Sheet> sheetIterator = book.sheetIterator();
            sheetIterator.hasNext();) {
            Sheet sheet = sheetIterator.next();

            sheets.put(sheet.getSheetName(), this.getSheet(sheet, withATitle));
        }

        return sheets;
    }

}
