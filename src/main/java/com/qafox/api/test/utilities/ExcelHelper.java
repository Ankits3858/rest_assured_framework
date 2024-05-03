package com.qafox.api.test.utilities;

public class ExcelHelper {

	String sheet;
	int row;
	ExcelManager excelManager;

	public ExcelHelper(ExcelManager excelManager, String sheet, int row) {
		this.sheet = sheet;
		this.row = row;
		this.excelManager = excelManager;
	}

	public ExcelHelper(ExcelManager excelManager, String sheet) {
		this.sheet = sheet;
		this.excelManager = excelManager;
	}

	public String getData(int col) {
		return excelManager.getCellData(sheet, col, row);
	}

	public String getData(int col, int row) {
		return excelManager.getCellData(sheet, col, row);
	}
}