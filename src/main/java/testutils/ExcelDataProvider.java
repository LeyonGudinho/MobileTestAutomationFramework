package testutils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;

public class ExcelDataProvider {

	@DataProvider(name = "VerifyProductPurchaseDataProvider")
	public Object[][] VerifyProductPurchaseDataProvider(Method context) throws IOException {
		String testMethod = "VerifyProductPurchase";

		//String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"	+ File.separator + "resources" + File.separator + "TestDataFile.xlsx";
		//String sheetName = testMethod.getMethodName();
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("TestDataFile.xlsx");
		String sheetName = testMethod;

		//FileInputStream fis = new FileInputStream(filePath);
		Workbook workbook = WorkbookFactory.create(inputStream);
		Sheet sheet = workbook.getSheet(sheetName);

		int rowCount = sheet.getLastRowNum();
		int colCount = sheet.getRow(0).getLastCellNum();

		Object[][] data = new Object[rowCount][colCount];

		for (int i = 0; i < rowCount; i++) {
			Row row = sheet.getRow(i + 1);
			for (int j = 0; j < colCount; j++) {
				Cell cell = row.getCell(j);
				data[i][j] = getCellValue(cell);
			}
		}

		workbook.close();
		inputStream.close();

		return data;
	}

	private static Object getCellValue(Cell cell) {
		Object cellValue = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case STRING:
				cellValue = cell.getStringCellValue();
				break;
			case NUMERIC:
				cellValue = cell.getNumericCellValue();
				break;
			case BOOLEAN:
				cellValue = cell.getBooleanCellValue();
				break;
			case FORMULA:
				cellValue = cell.getCellFormula();
				break;
			case BLANK:
				break;
			default:
				break;
			}
		}
		return cellValue;
	}
}
