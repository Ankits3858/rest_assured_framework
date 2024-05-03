package com.qafox.api.test.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;

public class ExcelManager {

	String filePath;
	private FileInputStream fileInputStream;
	private XSSFWorkbook xssfWorkbook;
	private XSSFSheet xssfSheet;
	private XSSFRow xssfRow;
	private XSSFCell xssfCell;
	private String cellText;
	private Logger log = LogManager.getLogger(ExcelManager.class);

	public ExcelManager(String filePath) {
		this.filePath = filePath;
		try {
			File file = new File(filePath);
			if (file.exists()) {
				fileInputStream = new FileInputStream(file);
				xssfWorkbook = new XSSFWorkbook(fileInputStream);
				fileInputStream.close();
			} else {
				log.error("File_'" + filePath + "' doesn't exist in resources folder");
				Assert.fail("File_'" + filePath + "' doesn't exist in resources folder");
			}
		} catch (FileNotFoundException e) {
			log.error("File_'" + filePath + "' doesn't exist in resources folder");
			Assert.fail("File_'" + filePath + "' doesn't exist in resources folder");
		} catch (IOException e) {
			log.error("Exception occured while accessing file");
			Assert.fail("Exception occured while accessing file");
		} catch (NullPointerException e) {
			log.error("File is path provided is null");
			Assert.fail("File is path provided is null");
		}
	}

	public boolean isSheetExist(String sheetName) {
		int index = 0;
		if (sheetName != null) {
			index = xssfWorkbook.getSheetIndex(sheetName);
		} else {
			log.error("Sheet name provided is null");
			Assert.fail("Sheet name provided is null");
		}
		if (index == -1) {
			log.error("Sheet with name " + sheetName + " doesn't exist");
			Assert.fail("Sheet with name " + sheetName + " doesn't exist");
			return false;
		} else {
			return true;
		}
	}

	public int getRowCount(String sheet) {
		int count = 0;
		if (isSheetExist(sheet)) {
			xssfSheet = xssfWorkbook.getSheet(sheet);
			count = xssfSheet.getLastRowNum() + 1;
		}
		return count;
	}

	public int getColumnCount(String sheet, int rowNumber) {
		int count = 0;
		if (isSheetExist(sheet)) {
			xssfSheet = xssfWorkbook.getSheet(sheet);
			xssfRow = xssfSheet.getRow(rowNumber);
			if (xssfRow == null)
				return -1;
			count = xssfRow.getLastCellNum();
		}
		return count;
	}

	public String getCellData(String sheetName, int columnNumber, int rowNumber) {
		if (isSheetExist(sheetName)) {
			if (rowNumber <= 0) {
				log.error("Row number should be great than zero.");
				Assert.fail("Row number should be great than zero.");
				return "";
			}
			xssfSheet = xssfWorkbook.getSheet(sheetName);
			xssfRow = xssfSheet.getRow(rowNumber);
			if (xssfRow == null)
				return "";
			xssfCell = xssfRow.getCell(columnNumber);
			if (xssfCell == null)
				return "";
			if (xssfCell.getCellType() == CellType.NUMERIC || xssfCell.getCellType() == CellType.FORMULA) {
				cellText = String.valueOf(xssfCell.getNumericCellValue());
				if (HSSFDateUtil.isCellDateFormatted(xssfCell)) {
					double date = xssfCell.getNumericCellValue();
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(HSSFDateUtil.getJavaDate(date));
					int year = calendar.get(Calendar.YEAR);
					int month = calendar.get(Calendar.MONTH);
					int day = calendar.get(Calendar.DAY_OF_MONTH);
					cellText = day + "/" + month + "/" + year;
				}
				return cellText;
			} else if (xssfCell.getCellType() == CellType.STRING) {
				return xssfCell.getStringCellValue();
			} else if (xssfCell.getCellType() == CellType.BOOLEAN) {
				return String.valueOf(xssfCell.getBooleanCellValue());
			} else {
				return "";
			}
		}
		return "";
	}

	public List<String> getRowData(String sheetName, int rowNumber) {
		List<String> list = new ArrayList<>();
		if (isSheetExist(sheetName)) {
			if (rowNumber <= 0) {
				log.error("Row number should be great than zero.");
				Assert.fail("Row number should be great than zero.");
				return null;
			}
			xssfSheet = xssfWorkbook.getSheet(sheetName);
			if (xssfRow == null)
				return null;
			if (rowNumber < 0 || rowNumber > getRowCount(sheetName)) {
				log.error("No such row exists in sheet");
				Assert.fail("No such row exists in sheet");
			}
			for (int i = 0; i < getColumnCount(sheetName, rowNumber); i++) {
				list.add(getCellData(sheetName, i, rowNumber));
			}
		}
		return list;
	}

	public String[][] getExcelSheetData(String sheet) {
		String[][] sheetData = null;
		try {
			if (isSheetExist(sheet)) {
				int rowCount = getRowCount(sheet) - 1;
				int colCount = getColumnCount(sheet, 0);
				sheetData = new String[rowCount][colCount];
				for (int i = 1; i <= rowCount + 1; i++)
					for (int j = 0; j < colCount; j++)
						sheetData[i - 1][j] = getCellData(sheet, j, i);
			}
		} catch (Exception e) {
		}
		return sheetData;
	}
}