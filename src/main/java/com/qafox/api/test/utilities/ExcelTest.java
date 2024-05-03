package com.qafox.api.test.utilities;

public class ExcelTest {

	private ExcelManager excelManager;
	private final String filePath = "src/main/java/com/qafox/api/test/testdata/test.xlsx";

	public void run() {
		excelManager = new ExcelManager(filePath);
		System.out.println(excelManager.isSheetExist("Sheet1"));
		System.out.println(excelManager.getRowCount("Sheet1"));
		System.out.println(excelManager.getColumnCount("Sheet1", 1));
		System.out.println(excelManager.getCellData("Sheet1", 1, 2));
		System.out.println(excelManager.getRowData("Sheet1", 5));
	}

	public static void main(String ar[]) {
		ExcelTest excelTest = new ExcelTest();
		excelTest.run();
	}
}