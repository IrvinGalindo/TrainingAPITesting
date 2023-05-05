package utils;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelDataHandler {
    private static XSSFWorkbook workbook;

    public static Object[][] getExcelTests(String excelFilePath, String sheetName, String executionFlag) {
        String file = System.getProperty("user.dir") + "/" + excelFilePath;

        XSSFSheet sheet = readExcel(file, sheetName);
        List<Map<String, Object>> dataList = getDataAsList(sheet);

        int i = 0;
        ArrayList<Map<String, Object>> enabledTests = new ArrayList<>();
        while (i < dataList.size()) {
            if (dataList.get(i).get(executionFlag) != null && dataList.get(i).get(executionFlag).toString().equalsIgnoreCase("Y")) {
                enabledTests.add(dataList.get(i));
            }
            i++;
        }
        Object[][] data = new Object[enabledTests.size()][2];
        for (int index = 0; index < enabledTests.size(); index++) {
            for (Map.Entry<String, Object> cell: enabledTests.get(index).entrySet()) {
                data[index][0] = cell.getKey();
                data[index][1] = cell.getValue();
            }

        }
        return data;
    }

    private static List<Map<String, Object>> getDataAsList(XSSFSheet sheet) {
        List<Map<String, Object>> sheetData = new ArrayList<>();
        int rowCount = sheet.getLastRowNum();

        if (rowCount > 0) {
            ArrayList<String> header = new ArrayList<>();
            XSSFRow row = sheet.getRow(0);
            int colCount = row.getPhysicalNumberOfCells();
            XSSFCell cell;
            for (int index = 0; index < colCount; index++) {
                cell = row.getCell(index);
                if (cell.getCellType() == CellType.STRING) {
                    header.add(cell.getStringCellValue());
                }
            }

            DataFormatter dataFormatter = new DataFormatter();
            for (int index = 1; index <= rowCount; index++) {
                Map<String, Object> map = new HashMap<>();
                row = sheet.getRow(index);
                if (row.getCell(1) != null) {

                    for (int i = 0; i < header.size(); i++) {
                        cell = row.getCell(i);
                        if (cell == null) {
                            map.put(header.get(index), null);
                        } else {
                            switch (cell.getCellType()) {
                                case _NONE:
                                    map.put(header.get(index), null);
                                    break;
                                case STRING:
                                    map.put(header.get(index), cell.getStringCellValue());
                                    break;
                                case NUMERIC:
                                    map.put(header.get(index), dataFormatter.formatCellValue(cell));
                                    break;
                                case BLANK:
                                    map.put(header.get(index), "");
                                    break;
                            }
                        }
                    }
                    sheetData.add(map);
                }
            }
        }
        return sheetData;

    }

    private static XSSFSheet readExcel(String file, String sheetName) {
        try {
            FileInputStream excelFile = new FileInputStream(Paths.get(file).toFile());
            workbook = new XSSFWorkbook(excelFile);

        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return workbook.getSheet(sheetName);


    }

}