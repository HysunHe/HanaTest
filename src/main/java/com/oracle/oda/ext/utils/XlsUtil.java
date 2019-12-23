/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with Hysun He. This information 
 * shall not be distributed or copied without written permission from 
 * Hysun He.
 *
 ***************************************************************************/

package com.oracle.oda.ext.utils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***************************************************************************
 * <PRE>
 *  Project Name    : bot-gateway-springboot
 * 
 *  Package Name    : com.oracle.oda.ext.utils
 * 
 *  File Name       : XlsUtil.java
 * 
 *  Creation Date   : 2019年2月25日
 * 
 *  Author          : hysun
 * 
 *  Purpose         : TODO
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public final class XlsUtil {
	public static final Logger LOGGER = LoggerFactory.getLogger(XlsUtil.class);

	public static String getStringCellValue(Cell cell) {
		try {
			return cell.getStringCellValue();
		} catch (IllegalStateException e) {
			return String.valueOf(cell.getNumericCellValue());
		}
	}

	public static int getIntCellValue(Cell cell) {
		try {
			return Double.valueOf(cell.getNumericCellValue()).intValue();
		} catch (Exception e) {
			LOGGER.warn("!!! Invalid int value in the xls cell, return 0 instead: " + cell.getStringCellValue());
			return 0;
		}
	}

	public static void autoSizeColumns(Sheet sheet, int columnNumber) {
		for (int i = 0; i < columnNumber; i++) {
			int orgWidth = sheet.getColumnWidth(i);
			sheet.autoSizeColumn(i, true);
			int newWidth = (int) (sheet.getColumnWidth(i) + 120);
			sheet.autoSizeColumn(i, false);
			if (newWidth > orgWidth) {
				sheet.setColumnWidth(i, newWidth);
			} else {
				sheet.setColumnWidth(i, orgWidth);
			}
		}
	}

	public static void setBorder(HSSFCellStyle style, BorderStyle border, HSSFColor color) {
		style.setBorderTop(border);
		style.setBorderLeft(border);
		style.setBorderRight(border);
		style.setBorderBottom(border);
		style.setTopBorderColor(color.getIndex());
		style.setLeftBorderColor(color.getIndex());
		style.setRightBorderColor(color.getIndex());
		style.setBottomBorderColor(color.getIndex());
	}

	public static void writeTitlesToExcel(HSSFWorkbook wb, Sheet sheet, String... titles) {
		HSSFCellStyle titleStyle = wb.createCellStyle();
		titleStyle.setAlignment(HorizontalAlignment.LEFT);
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		Row titleRow = createRow(sheet, 0);
		for (int i = 0; i < titles.length; i++) {
			Cell cell = titleRow.createCell(i);
			cell.setCellValue(titles[i]);
			cell.setCellStyle(titleStyle);
		}
	}

	public static Row createRow(Sheet sheet, int index) {
		Row row = sheet.createRow(index);
		row.setHeight(Double.valueOf(row.getHeight() * 1.5).shortValue());
		return row;
	}

	public static Cell strCell(Row row, int index) {
		Cell cell = row.createCell(index, CellType.STRING);
		return cell;
	}

	public static Cell numCell(Row row, int index) {
		Cell cell = row.createCell(index, CellType.NUMERIC);
		return cell;
	}
}
