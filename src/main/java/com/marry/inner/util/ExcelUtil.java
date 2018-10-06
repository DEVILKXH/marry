package com.marry.inner.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;

import com.marry.inner.base.annotation.ExcelColumn;
import com.marry.inner.base.annotation.ExcelTable;

public class ExcelUtil {

	private static String EXCEL_FILE = "file/EXCEL.xlsx";
	
	public static <T> XSSFWorkbook getWorkBook(List<T> t,Class<T> c) throws Exception {
		ClassPathResource resource = new ClassPathResource(EXCEL_FILE);
		XSSFWorkbook wb = new XSSFWorkbook(resource.getInputStream());
		XSSFSheet sheet = wb.getSheetAt(0);
		if(!c.isAnnotationPresent(ExcelTable.class)) {		
			return wb;
		}
		XSSFCellStyle alignCenter = wb.createCellStyle();
		alignCenter.setBorderBottom(BorderStyle.THIN);
		alignCenter.setBorderLeft(BorderStyle.THIN);
		alignCenter.setBorderRight(BorderStyle.THIN);
		alignCenter.setBorderTop(BorderStyle.THIN);
		alignCenter.setAlignment(HorizontalAlignment.CENTER);
		alignCenter.setVerticalAlignment(VerticalAlignment.CENTER);
		sheet.autoSizeColumn(0,true);
		alignCenter.setWrapText(true);
		XSSFRow row = getRow(sheet,0);
		XSSFCell cell = getCell(row,0);
		ExcelTable anno = c.getAnnotation(ExcelTable.class);
		String name = anno.name();
		cell.setCellValue(name);
		cell.setCellStyle(alignCenter);
		wb.setSheetName(0,name);
		Field []fields = c.getDeclaredFields();
		XSSFRow row2 = getRow(sheet,1);
		XSSFCell cell2 = getCell(row2,0);
		cell2.setCellValue("序号");
		cell2.setCellStyle(alignCenter);
		int length = 1;
		for(Field field: fields) {
			if(field.isAnnotationPresent(ExcelColumn.class)) {
				ExcelColumn eanno = field.getAnnotation(ExcelColumn.class);
				String columnName = eanno.name();
				int index = eanno.index();
				XSSFCell cell3 = getCell(row2,index);
				cell3.setCellValue(columnName);
				cell3.setCellStyle(alignCenter);
				length ++;
			}
		}
		CellRangeAddress cellRangeAddress = new CellRangeAddress(0,0,0,length - 1);//起始行,结束行,起始列,结束列  
		sheet.addMergedRegion(cellRangeAddress);
		
		int rowIndex = 2;
		for(T tt: t) {
			XSSFRow row3 = getRow(sheet, rowIndex);
			XSSFCell _cell = getCell(row3,0);
			_cell.setCellValue(rowIndex - 1);
			_cell.setCellStyle(alignCenter);
			for(Field field: fields) {
				if(field.isAnnotationPresent(ExcelColumn.class)) {
					ExcelColumn eanno = field.getAnnotation(ExcelColumn.class);
					int index = eanno.index();
					XSSFCell cell3 = getCell(row3,index);
					cell3.setCellValue(getValue(tt,field));
					cell3.setCellStyle(alignCenter);
				}
			}
			rowIndex ++;
		}
		return wb;
	}

	public static <T> String getValue(T tt, Field field) throws Exception{
		Class<?> c = tt.getClass();
		Method m = c.getDeclaredMethod(getGetMethod(field.getName()));
		Object o = m.invoke(tt);
		if(null == o) {
			return "";
		}
		return o.toString();
	}

	public static XSSFCell getCell(XSSFRow row, int i) {
		XSSFCell cell = row.getCell(i);
		if(null == cell) {
			cell = row.createCell(i);
		}
		return cell;
	}

	public static XSSFRow getRow(XSSFSheet sheet, int i) {
		XSSFRow row = sheet.getRow(i);
		if(null == row) {
			row = sheet.createRow(i);
		}
		return row;
	}
	
	public static String getGetMethod(String value) {
		char c = value.charAt(0);
		return "get" + Character.toUpperCase(c) + value.substring(1);
	}
}
