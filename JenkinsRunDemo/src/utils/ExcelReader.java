package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ExcelReader {

	private static Map<String, String[]> locators = new HashMap<>();
	
	static {
        loadLocators();
    }

	
	public static Object[][] readExcelData(String filePath, String sheetName) {
		Object[][] data = null;
		try (FileInputStream file = new FileInputStream(filePath); Workbook workbook = new XSSFWorkbook(file)) {

			Sheet sheet = workbook.getSheet(sheetName);
			int rowCount = sheet.getPhysicalNumberOfRows();
			int colCount = sheet.getRow(0).getPhysicalNumberOfCells();

			data = new Object[rowCount - 1][colCount]; // Excluding header row

			for (int i = 1; i < rowCount; i++) {
				Row row = sheet.getRow(i);
				for (int j = 0; j < colCount; j++) {
					data[i - 1][j] = row.getCell(j).toString();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
	
	 private static void loadLocators() {
		 String filePath = "test-data/ElementLocators.xlsx";
		 String sheetName = "Locators";
	        try (FileInputStream file = new FileInputStream(filePath);
	             Workbook workbook = new XSSFWorkbook(file)) {
	            
	            Sheet sheet = workbook.getSheet(sheetName);
	            Iterator<Row> rowIterator = sheet.iterator();
	            
	            if (rowIterator.hasNext()) rowIterator.next(); // Skip header row
	            
	            while (rowIterator.hasNext()) {
	                Row row = rowIterator.next();
	                Iterator<Cell> cellIterator = row.cellIterator();
	                
	                String elementName = cellIterator.next().getStringCellValue();
	                String locatorType = cellIterator.next().getStringCellValue();
	                String locatorValue = cellIterator.next().getStringCellValue();
	                
	                locators.put(elementName, new String[]{locatorType, locatorValue});
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    public static String[] getLocator(String elementName) {
	        return locators.get(elementName);
	    }
}
