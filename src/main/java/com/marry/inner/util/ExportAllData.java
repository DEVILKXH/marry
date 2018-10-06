package com.marry.inner.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;

import com.marry.inner.vo.AllExcelData;

public class ExportAllData {

	private static String EXCEL_FILE = "file/templates.xlsx";
	private static String EXCEL_FILE2 = "file/g_templates.xlsx";
	
	private static Map<String, Integer> rowMap;
	private static Map<String, Integer> rowMap2;
	private static Map<String, Integer> cellMap;
	private static Map<String, Integer> cellMap2;
	
	public static XSSFWorkbook getWorkBook(List<AllExcelData> list, List<AllExcelData> list2, List<AllExcelData> list3, List<AllExcelData> list4) throws Exception {
		initRowMap();
		initRowMap2();
		initcellMap();
		ClassPathResource resource = new ClassPathResource(EXCEL_FILE);
		XSSFWorkbook wb = new XSSFWorkbook(resource.getInputStream());
		XSSFSheet sheet = wb.getSheet("sheet");
		XSSFSheet sheet2 = wb.getSheet("Sheet1");
		sheet.setForceFormulaRecalculation(true);
		sheet2.setForceFormulaRecalculation(true);
		int rowIndex = 167;
		for(AllExcelData d: list) {
			String rowkey = d.getFdCategory() + "-" + d.getFdName() + "-" + d.getFdPriceRange();
			String columnKey = d.getFdStyle(); 
			if(d.getFdColorType() != null) {
				columnKey += d.getFdColorType();
			}
			Integer rowNum = rowMap.get(rowkey);
			if(rowNum == null) {
				insertRow(ExcelUtil.getRow(sheet, rowIndex++), new String[]{d.getFdCategory(), d.getFdName(), d.getFdPriceRange(), d.getFdStyle(), d.getFdColorType(), d.getFdPitchOn()}, 0);
				continue;
			}
			Integer cellNum = cellMap.get(columnKey);
			if(cellNum == null) {
				continue;
			}
			XSSFRow row = ExcelUtil.getRow(sheet, rowNum);
			XSSFCell cell = ExcelUtil.getCell(row, cellNum);
			cell.setCellValue(Integer.parseInt(d.getFdPitchOn()));
 		}
		
		int rowIndex2 = 35;
		for(AllExcelData d: list2) {
			String rowkey = d.getFdCategory() + "-" + d.getFdName();
			String columnKey = d.getFdStyle(); 
			if(d.getFdColorType() != null) {
				columnKey += d.getFdColorType();
			}
			Integer rowNum = rowMap2.get(rowkey);
			if(rowNum == null) {
				insertRow(ExcelUtil.getRow(sheet2, rowIndex2++), new String[]{d.getFdCategory(), d.getFdName(), d.getFdStyle(), d.getFdColorType(), d.getFdPitchOn()}, 0);
				continue;
			}
			Integer cellNum = cellMap.get(columnKey);
			if(cellNum == null) {
				continue;
			}
			XSSFRow row = ExcelUtil.getRow(sheet2, rowNum);
			XSSFCell cell = ExcelUtil.getCell(row, cellNum);
			cell.setCellValue(Integer.parseInt(d.getFdPitchOn()));
 		}
		
		for(AllExcelData d: list3) {
			String rowkey = d.getFdCategory() + "-" + d.getFdName() + "-" + d.getFdPriceRange();
			String columnKey = d.getFdSeason(); 
			if(d.getFdColorType() != null) {
				columnKey += d.getFdColorType();
			}
			Integer rowNum = rowMap.get(rowkey);
			if(rowNum == null) {
				insertRow(ExcelUtil.getRow(sheet, rowIndex++), new String[]{d.getFdCategory(), d.getFdName(), d.getFdSeason()}, 0);
				continue;
			}
			Integer cellNum = cellMap.get(columnKey);
			if(cellNum == null) {
				continue;
			}
			XSSFRow row = ExcelUtil.getRow(sheet, rowNum);
			XSSFCell cell = ExcelUtil.getCell(row, cellNum);
			cell.setCellValue(Integer.parseInt(d.getFdPitchOn()));
 		}
		
		for(AllExcelData d: list4) {
			String rowkey = d.getFdCategory() + "-" + d.getFdName();
			String columnKey = d.getFdSeason(); 
			Integer rowNum = rowMap2.get(rowkey);
			if(rowNum == null) {
				continue;
			}
			Integer cellNum = cellMap.get(columnKey);
			if(cellNum == null) {
				continue;
			}
			XSSFRow row = ExcelUtil.getRow(sheet2, rowNum);
			XSSFCell cell = ExcelUtil.getCell(row, cellNum);
			cell.setCellValue(Integer.parseInt(d.getFdPitchOn()));
 		}
		return wb;
	}

	private static void insertRow(XSSFRow row, String []d, int cellNum) {
		
		for(String s: d) {
			XSSFCell cell = ExcelUtil.getCell(row, cellNum);
			cell.setCellValue(s == null? "": s);
			cellNum ++;
		}
	}


	private static void initcellMap() {
		cellMap = new HashMap<>();
		cellMap.put("正装A1", 17);
		cellMap.put("商务休闲B1", 18);
		cellMap.put("休闲D1", 19);
		
		cellMap.put("正装A2", 20);
		cellMap.put("商务休闲B2", 21);
		cellMap.put("休闲D2", 22);

		cellMap.put("正装A3", 23);
		cellMap.put("商务休闲", 24);
		cellMap.put("休闲D3", 26);

		cellMap.put("正装A4", 26);
		cellMap.put("商务休闲B4", 27);
		cellMap.put("休闲D4", 28);
		
		cellMap.put("春季", 29);
		cellMap.put("夏季", 30);
	}


	private static void initRowMap() {
		rowMap = new HashMap<>();
		rowMap.put("裤类-便装裤-400-499", 126);
		rowMap.put("裤类-便装裤-500-599", 127);
		rowMap.put("裤类-便装裤-600-699", 128);
		rowMap.put("裤类-便装裤-700-799", 129);
		rowMap.put("裤类-便装裤-800-899", 130);

		rowMap.put("裤类-仿毛西裤-300-399", 132);
		rowMap.put("裤类-仿毛西裤-400-499", 133);
		rowMap.put("裤类-仿毛西裤-500-599", 134);

		rowMap.put("裤类-毛料西裤-700-799", 136);
		rowMap.put("裤类-毛料西裤-800-899", 137);
		rowMap.put("裤类-毛料西裤-900-999", 138);
		rowMap.put("裤类-毛料西裤-1000-1199", 139);
		rowMap.put("裤类-毛料西裤-1600-1799", 140);
		rowMap.put("裤类-毛料西裤-1200-1399", 141);
		rowMap.put("裤类-毛料西裤-2600-2799", 142);
		rowMap.put("裤类-毛料西裤-3000以上", 143);

		rowMap.put("裤类-牛仔裤-300-399", 145);
		rowMap.put("裤类-牛仔裤-400-499", 146);
		rowMap.put("裤类-牛仔裤-500-599", 147);
		rowMap.put("裤类-牛仔裤-600-699", 148);
		rowMap.put("裤类-牛仔裤-700-799", 149);
		rowMap.put("裤类-牛仔裤-800-899", 150);
		
		rowMap.put("裤类-休闲裤-400-499", 152);
		rowMap.put("裤类-休闲裤-500-599", 153);
		rowMap.put("裤类-休闲裤-600-699", 154);
		rowMap.put("裤类-休闲裤-700-799", 155);
		rowMap.put("裤类-休闲裤-800-899", 156);
		rowMap.put("裤类-休闲裤-900-999", 157);
		rowMap.put("裤类-休闲裤-1000-1199", 158);
		rowMap.put("裤类-休闲裤-1200-1399", 159);
		rowMap.put("裤类-休闲裤-1600-1799", 160);
		
		rowMap.put("裤类-休闲短裤-400-499", 162);
		rowMap.put("裤类-牛仔短裤-400-499", 164);
		
		rowMap.put("外套类-单西-900-999", 4);
		rowMap.put("外套类-单西-1000-1199", 5);
		rowMap.put("外套类-单西-1200-1399", 6);
		rowMap.put("外套类-单西-1400-1599", 7);
		rowMap.put("外套类-单西-1600-1799", 8);
		rowMap.put("外套类-单西-1800-1999", 9);
		rowMap.put("外套类-单西-2000-2199", 10);
		rowMap.put("外套类-单西-2299-2499", 11);
		rowMap.put("外套类-单西-2599-2899", 12);
		rowMap.put("外套类-单西-2899以上", 13);

		rowMap.put("外套类-仿毛套西-1200-1399", 15);
		rowMap.put("外套类-仿毛套西-1400-1599", 16);
		rowMap.put("外套类-仿毛套西-1600-1799", 17);
		rowMap.put("外套类-仿毛套西-1799以上", 18);

		rowMap.put("外套类-毛料套西-1600-1799", 20);
		rowMap.put("外套类-毛料套西-2000-2199", 21);
		rowMap.put("外套类-毛料套西-2400-2599", 22);
		rowMap.put("外套类-毛料套西-2600-2799", 23);
		rowMap.put("外套类-毛料套西-3000以上", 24);

		rowMap.put("外套类-便装-900-999", 26);
		rowMap.put("外套类-便装-1000-1199", 27);
		rowMap.put("外套类-便装-1400-1599", 28);
		rowMap.put("外套类-便装-1600以上", 29);

		rowMap.put("外套类-派克-1200-1399", 31);
		rowMap.put("外套类-派克-1400以上", 32);

		rowMap.put("外套类-茄克-700-799", 34);
		rowMap.put("外套类-茄克-800-899", 35);
		rowMap.put("外套类-茄克-900-999", 36);
		rowMap.put("外套类-茄克-1000-1199", 37);
		rowMap.put("外套类-茄克-1200-1399", 38);
		rowMap.put("外套类-茄克-1400-1599", 39);
		rowMap.put("外套类-茄克-1600以上", 40);

		rowMap.put("外套类-毛衫-700-799", 42);
		rowMap.put("外套类-毛衫-800以上", 43);

		rowMap.put("外套类-羽绒茄克-1200-1399", 45);
		rowMap.put("外套类-羽绒茄克-1400以上", 46);

		rowMap.put("内搭类-针织长袖衬衫-700-799", 49);
		rowMap.put("内搭类-针织长袖衬衫-800-899", 50);
		rowMap.put("内搭类-针织长袖衬衫-900-999", 51);
		rowMap.put("内搭类-针织长袖衬衫-1000-1199", 52);
		rowMap.put("内搭类-针织长袖衬衫-1200-1399", 53);
		rowMap.put("内搭类-针织长袖衬衫-1400以上", 54);

		rowMap.put("内搭类-正装长袖衬衫-300-399", 56);
		rowMap.put("内搭类-正装长袖衬衫-400-499", 57);
		rowMap.put("内搭类-正装长袖衬衫-500-599", 58);
		rowMap.put("内搭类-正装长袖衬衫-600-699", 59);
		rowMap.put("内搭类-正装长袖衬衫-700-799", 60);
		rowMap.put("内搭类-正装长袖衬衫-800-899", 61);
		rowMap.put("内搭类-正装长袖衬衫-900-999", 62);
		rowMap.put("内搭类-正装长袖衬衫-1000以上", 63);

		rowMap.put("内搭类-休闲长袖衬衫-400-499", 65);
		rowMap.put("内搭类-休闲长袖衬衫-500-599", 66);
		rowMap.put("内搭类-休闲长袖衬衫-600-699", 67);
		rowMap.put("内搭类-休闲长袖衬衫-700-799", 68);
		rowMap.put("内搭类-休闲长袖衬衫-800-899", 69);
		rowMap.put("内搭类-休闲长袖衬衫-900-999", 70);
		rowMap.put("内搭类-休闲长袖衬衫-1000以上", 71);

		rowMap.put("内搭类-长袖T恤-400-499", 73);
		rowMap.put("内搭类-长袖T恤-500-599", 74);
		rowMap.put("内搭类-长袖T恤-600-699", 75);
		rowMap.put("内搭类-长袖T恤-700-799", 76);
		rowMap.put("内搭类-长袖T恤-800-899", 77);
		rowMap.put("内搭类-长袖T恤-900-999", 78);
		rowMap.put("内搭类-长袖T恤-1000以上", 79);

		rowMap.put("内搭类-短袖T恤-400-499", 83);
		rowMap.put("内搭类-短袖T恤-500-599", 84);
		rowMap.put("内搭类-短袖T恤-600-699", 85);
		rowMap.put("内搭类-短袖T恤-700-799", 86);
		rowMap.put("内搭类-短袖T恤-800-899", 87);
		rowMap.put("内搭类-短袖T恤-900-999", 88);
		rowMap.put("内搭类-短袖T恤-1000-1199", 89);
		rowMap.put("内搭类-短袖T恤-1200-1399", 90);
		rowMap.put("内搭类-短袖T恤-1400-1599", 91);
		rowMap.put("内搭类-短袖T恤-1600-1799", 92);
		rowMap.put("内搭类-短袖T恤-1800以上", 93);

		rowMap.put("内搭类-针织短袖衬衫-500-599", 95);
		rowMap.put("内搭类-针织短袖衬衫-600-699", 96);
		rowMap.put("内搭类-针织短袖衬衫-700-799", 97);
		rowMap.put("内搭类-针织短袖衬衫-800-899", 98);
		rowMap.put("内搭类-针织短袖衬衫-900-999", 99);
		rowMap.put("内搭类-针织短袖衬衫-1000-1199", 100);
		rowMap.put("内搭类-针织短袖衬衫-1200-1399", 101);
		rowMap.put("内搭类-针织短袖衬衫-1400-1599", 102);
		rowMap.put("内搭类-针织短袖衬衫-1600-1799", 103);
		rowMap.put("内搭类-针织短袖衬衫-1800以上", 104);

		rowMap.put("内搭类-正装短袖衬衫-300-399", 106);
		rowMap.put("内搭类-正装短袖衬衫-400-499", 107);
		rowMap.put("内搭类-正装短袖衬衫-500-599", 108);
		rowMap.put("内搭类-正装短袖衬衫-600-699", 109);
		rowMap.put("内搭类-正装短袖衬衫-700-799", 110);
		rowMap.put("内搭类-正装短袖衬衫-800-899", 111);
		rowMap.put("内搭类-正装短袖衬衫-900-999", 112);
		rowMap.put("内搭类-正装短袖衬衫-1000以上", 113);

		rowMap.put("内搭类-休闲短袖衬衫-300-399", 115);
		rowMap.put("内搭类-休闲短袖衬衫-400-499", 116);
		rowMap.put("内搭类-休闲短袖衬衫-500-599", 117);
		rowMap.put("内搭类-休闲短袖衬衫-600-699", 118);
		rowMap.put("内搭类-休闲短袖衬衫-700-799", 119);
		rowMap.put("内搭类-休闲短袖衬衫-800-899", 120);
		rowMap.put("内搭类-休闲短袖衬衫-900-999", 121);
		rowMap.put("内搭类-休闲短袖衬衫-1000以上", 122);

	}
	
	private static void initRowMap2() {
		rowMap2 = new HashMap<>();
		rowMap2.put("外套类-单西", 4);
		rowMap2.put("外套类-仿毛套西", 5);
		rowMap2.put("外套类-毛料套西", 6);
		rowMap2.put("外套类-便装", 7);
		rowMap2.put("外套类-派克", 8);
		rowMap2.put("外套类-茄克", 9);
		rowMap2.put("外套类-毛衫", 10);
		rowMap2.put("外套类-羽绒茄克", 11);

		rowMap2.put("内搭类-针织长袖衬衫", 13);
		rowMap2.put("内搭类-正装长袖衬衫", 14);
		rowMap2.put("内搭类-休闲长袖衬衫", 15);
		rowMap2.put("内搭类-长袖T恤", 16);

		rowMap2.put("内搭类-短袖T恤", 19);
		rowMap2.put("内搭类-针织短袖衬衫", 20);
		rowMap2.put("内搭类-正装短袖衬衫", 21);
		rowMap2.put("内搭类-休闲短袖衬衫", 22);

		rowMap2.put("裤类-便装裤", 25);
		rowMap2.put("裤类-仿毛西裤", 26);
		rowMap2.put("裤类-毛料西裤", 27);
		rowMap2.put("裤类-牛仔裤", 28);
		rowMap2.put("裤类-休闲裤", 29);
		rowMap2.put("裤类-休闲短裤", 30);
		rowMap2.put("裤类-牛仔短裤", 31);

	}

	public static void initGcellMap() {
		cellMap = new HashMap<>();
		cellMap.put("时尚商务B1", 13);
		cellMap.put("时尚休闲D1", 14);
		cellMap.put("时尚休闲D2", 15);
		
		cellMap.put("时尚商务B3", 16);
		cellMap.put("时尚休闲D3A", 17);
		cellMap.put("时尚休闲D3B", 18);
		
		cellMap.put("时尚商务B4", 19);
		cellMap.put("时尚休闲D4", 20);
	}
	
	public static void initGcellMap2() {
		cellMap2 = new HashMap<>();
		cellMap2.put("时尚商务B1", 7);
		cellMap2.put("时尚休闲D1", 8);
		cellMap2.put("时尚休闲D2", 9);
		
		cellMap2.put("时尚商务B3", 10);
		cellMap2.put("时尚休闲D3A", 11);
		cellMap2.put("时尚休闲D3B", 12);
		
		cellMap2.put("时尚商务B4", 13);
		cellMap2.put("时尚休闲D4", 14);
	}
	
	public static void initGRowMap() {
		rowMap = new HashMap<>();
		rowMap.put("裤类-西裤-300-399",4);
		rowMap.put("裤类-西裤-400-499",5);
		rowMap.put("裤类-西裤-500-599",6);
		rowMap.put("裤类-西裤-600-699",7);
		rowMap.put("裤类-西裤-700-799",8);
		rowMap.put("裤类-西裤-800以上",9);

		rowMap.put("裤类-牛仔裤-400-499",11);
		rowMap.put("裤类-牛仔裤-500-599",12);
		rowMap.put("裤类-牛仔裤-600-699",13);
		rowMap.put("裤类-牛仔裤-700-799",14);
		rowMap.put("裤类-牛仔裤-800以上",15);

		rowMap.put("裤类-休闲裤-300-399",17);
		rowMap.put("裤类-休闲裤-400-499",18);
		rowMap.put("裤类-休闲裤-500-599",19);
		rowMap.put("裤类-休闲裤-600-699",20);
		rowMap.put("裤类-休闲裤-700-799",21);
		rowMap.put("裤类-休闲裤-800以上",22);

		rowMap.put("裤类-休闲短裤-300-399",24);
		rowMap.put("裤类-休闲短裤-400-499",25);
		rowMap.put("裤类-休闲短裤-500-599",26);
		rowMap.put("裤类-休闲短裤-600-699",27);

		rowMap.put("裤类-牛仔短裤-300-399",29);
		rowMap.put("裤类-牛仔短裤-400-499",30);
		rowMap.put("裤类-牛仔短裤-500-599",31);
		rowMap.put("裤类-牛仔短裤-600-699",32);
		
		rowMap.put("内衬-短袖T恤-300-399",35);
		rowMap.put("内衬-短袖T恤-400-499",36);
		rowMap.put("内衬-短袖T恤-500-599",37);
		rowMap.put("内衬-短袖T恤-600-699",38);
		rowMap.put("内衬-短袖T恤-700-799",39);
		rowMap.put("内衬-短袖T恤-800-899",40);
		rowMap.put("内衬-短袖T恤-900-1199",41);
		rowMap.put("内衬-短袖T恤-1200以上",42);

		rowMap.put("内衬-毛衫-600-699",44);
		rowMap.put("内衬-毛衫-700-799",45);
		rowMap.put("内衬-毛衫-800-899",46);
		rowMap.put("内衬-毛衫-900以上",47);

		rowMap.put("内衬-卫衣-400-499",49);
		rowMap.put("内衬-卫衣-500-599",50);
		rowMap.put("内衬-卫衣-600-699",51);
		rowMap.put("内衬-卫衣-700-799",52);
		rowMap.put("内衬-卫衣-800-899",53);
		rowMap.put("内衬-卫衣-900以上",54);

		rowMap.put("内衬-短袖衬衫-300-399",56);
		rowMap.put("内衬-短袖衬衫-400-499",57);
		rowMap.put("内衬-短袖衬衫-500-599",58);
		rowMap.put("内衬-短袖衬衫-600-699",59);
		rowMap.put("内衬-短袖衬衫-700-799",60);
		rowMap.put("内衬-短袖衬衫-800以上",61);

		rowMap.put("内衬-长袖衬衫-300-399",63);
		rowMap.put("内衬-长袖衬衫-400-499",64);
		rowMap.put("内衬-长袖衬衫-500-599",65);
		rowMap.put("内衬-长袖衬衫-600-699",66);
		rowMap.put("内衬-长袖衬衫-700-799",67);
		rowMap.put("内衬-长袖衬衫-800以上",68);

		rowMap.put("外搭-背心-900-999",71);
		rowMap.put("外搭-背心-1000以上",72);

		rowMap.put("外搭-单西-900-1099",74);
		rowMap.put("外搭-单西-1100-1199",75);
		rowMap.put("外搭-单西-1200-1399",76);
		rowMap.put("外搭-单西-1400-1599",77);
		rowMap.put("外搭-单西-1660-1899",78);
		rowMap.put("外搭-单西-1900以上",79);

		rowMap.put("外搭-派克-1200-1390",81);
		rowMap.put("外搭-派克-1400-1699",82);
		rowMap.put("外搭-派克-1200以下",83);
		rowMap.put("外搭-派克-1700以上",84);

		rowMap.put("外搭-茄克-800-899",86);
		rowMap.put("外搭-茄克-900-1099",87);
		rowMap.put("外搭-茄克-1100-1399",88);
		rowMap.put("外搭-茄克-1400-1599",89);
		rowMap.put("外搭-茄克-1600-1899",90);

		rowMap.put("外搭-套西-900-999",92);
		rowMap.put("外搭-套西-1000-1499",93);
		rowMap.put("外搭-套西-1500-1699",94);
		rowMap.put("外搭-套西-1700-1999",95);
		rowMap.put("外搭-套西-2000-2299",96);
		rowMap.put("外搭-套西-2300以上",97);

		rowMap.put("外搭-休闲套装-1000-1199",99);
		rowMap.put("外搭-休闲套装-1200-1399",100);
		rowMap.put("外搭-休闲套装-1400-1599",101);
		rowMap.put("外搭-休闲套装-1600以上",102);

		rowMap.put("外搭-羽绒茄克-1200-1399",104);
		rowMap.put("外搭-羽绒茄克-1400-1599",105);
		rowMap.put("外搭-羽绒茄克-1600-1899",106);
		rowMap.put("外搭-羽绒茄克-1900-2299",107);

	}
	public static void initGRowMap2() {
		rowMap2 = new HashMap<>();
		rowMap2.put("裤类-西裤",4);
		rowMap2.put("裤类-牛仔裤",5);
		rowMap2.put("裤类-休闲裤",6);
		rowMap2.put("裤类-便装裤",7);
		rowMap2.put("裤类-牛仔短裤",8);
		rowMap2.put("裤类-休闲短裤",9);
		
		rowMap2.put("内搭类-卫衣",10);
		rowMap2.put("内搭类-毛衫",11);
		rowMap2.put("内搭类-短袖T恤",12);
		rowMap2.put("内搭类-长袖衬衫",13);
		rowMap2.put("内搭类-正装长袖衬衫",14);
		rowMap2.put("内搭类-短袖衬衫",15);
		
		rowMap2.put("外套类-背心",16);
		rowMap2.put("外套类-单西",17);
		rowMap2.put("外套类-茄克",18);
		rowMap2.put("外套类-派克",19);
		rowMap2.put("外套类-套西",20);
		rowMap2.put("外套类-休闲套装",21);
		rowMap2.put("外套类-仿毛套西",22);
		rowMap2.put("外套类-羽绒茄克",23);
		rowMap2.put("外套类-羽绒派克",24);

	}
	
	public static XSSFWorkbook getWorkBook2(List<AllExcelData> list, List<AllExcelData> list2, List<AllExcelData> list3,
			List<AllExcelData> list4) throws IOException {
		initGRowMap();
		initGRowMap2();
		initGcellMap();
		initGcellMap2();
		ClassPathResource resource = new ClassPathResource(EXCEL_FILE2);
		XSSFWorkbook wb = new XSSFWorkbook(resource.getInputStream());
		XSSFSheet sheet = wb.getSheetAt(1);
		XSSFSheet sheet2 = wb.getSheetAt(0);
		sheet.setForceFormulaRecalculation(true);
		sheet2.setForceFormulaRecalculation(true);
		int rowIndex = 113;
		for(AllExcelData d: list) {
			String rowkey = d.getFdCategory() + "-" + d.getFdName() + "-" + d.getFdPriceRange();
			String columnKey = d.getFdStyle(); 
			if(d.getFdColorType() != null) {
				columnKey += d.getFdColorType();
			}
			Integer rowNum = rowMap.get(rowkey);
			if(rowNum == null) {
				insertRow(ExcelUtil.getRow(sheet, rowIndex++), new String[]{d.getFdCategory(), d.getFdName(), d.getFdPriceRange(), d.getFdStyle(), d.getFdColorType(), d.getFdPitchOn()}, 0);
				continue;
			}
			Integer cellNum = cellMap.get(columnKey);
			if(cellNum == null) {
				continue;
			}
			XSSFRow row = ExcelUtil.getRow(sheet, rowNum);
			XSSFCell cell = ExcelUtil.getCell(row, cellNum);
			cell.setCellValue(Integer.parseInt(d.getFdPitchOn()));
 		}
		
		int rowIndex2 = 31;
		for(AllExcelData d: list2) {
			String rowkey = d.getFdCategory() + "-" + d.getFdName();
			String columnKey = d.getFdStyle(); 
			if(d.getFdColorType() != null) {
				columnKey += d.getFdColorType();
			}
			Integer rowNum = rowMap2.get(rowkey);
			if(rowNum == null) {
				insertRow(ExcelUtil.getRow(sheet2, rowIndex2++), new String[]{d.getFdCategory(), d.getFdName(), d.getFdStyle(), d.getFdColorType(), d.getFdPitchOn()}, 0);
				continue;
			}
			Integer cellNum = cellMap2.get(columnKey);
			if(cellNum == null) {
				continue;
			}
			XSSFRow row = ExcelUtil.getRow(sheet2, rowNum);
			XSSFCell cell = ExcelUtil.getCell(row, cellNum);
			cell.setCellValue(Integer.parseInt(d.getFdPitchOn()));
 		}
		
		for(AllExcelData d: list3) {
			String rowkey = d.getFdCategory() + "-" + d.getFdName() + "-" + d.getFdPriceRange();
			String columnKey = d.getFdSeason(); 
			if(d.getFdColorType() != null) {
				columnKey += d.getFdColorType();
			}
			Integer rowNum = rowMap.get(rowkey);
			if(rowNum == null) {
				insertRow(ExcelUtil.getRow(sheet, rowIndex++), new String[]{d.getFdCategory(), d.getFdName(), d.getFdSeason()}, 0);
				continue;
			}
			Integer cellNum = cellMap.get(columnKey);
			if(cellNum == null) {
				continue;
			}
			XSSFRow row = ExcelUtil.getRow(sheet, rowNum);
			XSSFCell cell = ExcelUtil.getCell(row, cellNum);
			cell.setCellValue(Integer.parseInt(d.getFdPitchOn()));
 		}
		
		for(AllExcelData d: list4) {
			String rowkey = d.getFdCategory() + "-" + d.getFdName();
			String columnKey = d.getFdSeason(); 
			Integer rowNum = rowMap2.get(rowkey);
			if(rowNum == null) {
				continue;
			}
			Integer cellNum = cellMap2.get(columnKey);
			if(cellNum == null) {
				continue;
			}
			XSSFRow row = ExcelUtil.getRow(sheet2, rowNum);
			XSSFCell cell = ExcelUtil.getCell(row, cellNum);
			cell.setCellValue(Integer.parseInt(d.getFdPitchOn()));
 		}
		return wb;
	}
}
