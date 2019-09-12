package com.zrytech.framework.newshop.utils;

import org.apache.poi.hssf.usermodel.*;

import java.io.FileOutputStream;
import java.util.List;

public class ExcelUtils {

    public ExcelUtils() {


    }

    public static HSSFCell getCell(HSSFSheet sheet, int row, int col) {

        HSSFRow sheetRow = sheet.getRow(row);

        if (sheetRow == null) {

            sheetRow = sheet.createRow(row);

        }

        HSSFCell cell = sheetRow.getCell(col);

        if (cell == null) {

            cell = sheetRow.createCell(col);

        }

        return cell;

    }

    public static void setText(HSSFCell cell, String text) {

        cell.setCellType(HSSFCell.CELL_TYPE_STRING);

        cell.setCellValue(text);

    }

    public static void toExcel(List<String> titles, List<DataBean> list , String fileName) throws Exception {

        HSSFWorkbook wb = new HSSFWorkbook(); // 定义一个新的工作簿

        HSSFSheet sheet = wb.createSheet("第一个Sheet页");  // 创建第一个Sheet页

        // 第四步，创建单元格，并设置值表头 设置表头居中  

        HSSFCellStyle style = wb.createCellStyle();

        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        HSSFRow row = sheet.createRow(0); // 创建一个行

        HSSFCell cell = row.createCell(0); // 创建一个单元格  第1列

        //cell.setCellValue(new Date());  // 给单元格设置值

        for (int i = 0; i < titles.size(); i++) { //设置标题

            String title = titles.get(i);

            cell = getCell(sheet, 0, i);

            setText(cell, title);

            cell.setCellStyle(style);

        }

        for (int i = 0; i < list.size(); i++) {

            DataBean vpd = list.get(i);

            for (int j = 0; j < titles.size(); j++) {

//                String varstr = vpd.getString("var" + (j + 1)) != null ? vpd.getString("var" + (j + 1)) : "";
                String varstr = vpd.getString("var" + (j + 1)) != null ? vpd.getString("var" + (j + 1)) : "";

                cell = getCell(sheet, i + 1, j);

                setText(cell, varstr);

                cell.setCellStyle(style);

            }

        }


        FileOutputStream fileOut = new FileOutputStream(fileName);

        wb.write(fileOut);

        fileOut.close();

        wb.close();

    }
}