package com.cmcc.medicalcare.utils;

import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cmcc.medicalcare.model.InquiryMessageLog;


/**
 * 写Excel工具类
 * @author zds
 *
 */
public class WriteExcel {
    
    /**
     * 统计活跃用户数及活跃总次数_导出Excel信息 
     * @param inquiryMessageLogList
     * @return
     * @throws IOException
     */
	public static XSSFWorkbook exportUserCountToExcel(List<InquiryMessageLog> inquiryMessageLogList) throws IOException {
        XSSFWorkbook workBook = new XSSFWorkbook(); // 创建一个workbook 对应一个excel应用文件
        XSSFSheet sheet = workBook.createSheet("Sheet1"); // 在workbook中添加一个sheet,对应Excel文件中的sheet,Sheet名称，可以自定义中文名称
        XSSFRow headRow = sheet.createRow(0); // 构建表头
        XSSFCell cell = null;
        XSSFCellStyle headStyle = getHeadStyle(workBook); //表头样式
        XSSFCellStyle bodyStyle = getBodyStyle(workBook); //表体样式
        
        String[] titles = { "序号", "所属机构", "活跃用户数", "使用总次数"};
 
        // 输出标题
        for (int i = 0; i < titles.length; i++) {
            cell = headRow.createCell(i);
            cell.setCellStyle(headStyle);
            cell.setCellValue(titles[i]);
        }
        // 构建表体数据
        for (int j = 0; j < inquiryMessageLogList.size(); j++) {
            XSSFRow bodyRow = sheet.createRow(j + 1);
            InquiryMessageLog inquiryMessageLog = inquiryMessageLogList.get(j);
 
            cell = bodyRow.createCell(0);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(j + 1);
 
            cell = bodyRow.createCell(1);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(inquiryMessageLog.getHospital());
            
            cell = bodyRow.createCell(2);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(inquiryMessageLog.getUserCount());
             
            cell = bodyRow.createCell(3);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(inquiryMessageLog.getTotalCount());
        }
        
		return workBook;
	}
	
	/**
     * 统计医疗机构每个用户使用次数_导出Excel信息
     * @param inquiryMessageLogList
     * @return
     * @throws IOException
     */
	public static XSSFWorkbook exportUserCountDetailsToExcel(List<InquiryMessageLog> inquiryMessageLogList) throws IOException {
        XSSFWorkbook workBook = new XSSFWorkbook(); // 创建一个workbook 对应一个excel应用文件
        XSSFSheet sheet = workBook.createSheet("Sheet1"); // 在workbook中添加一个sheet,对应Excel文件中的sheet,Sheet名称，可以自定义中文名称
        XSSFRow headRow = sheet.createRow(0); // 构建表头
        XSSFCell cell = null;
        XSSFCellStyle headStyle = getHeadStyle(workBook); //表头样式
        XSSFCellStyle bodyStyle = getBodyStyle(workBook); //表体样式
        
        String[] titles = { "序号", "所属机构", "用户手机号码", "用户姓名", "使用次数"};
 
        // 输出标题
        for (int i = 0; i < titles.length; i++) {
            cell = headRow.createCell(i);
            cell.setCellStyle(headStyle);
            cell.setCellValue(titles[i]);
        }
        // 构建表体数据
        for (int j = 0; j < inquiryMessageLogList.size(); j++) {
            XSSFRow bodyRow = sheet.createRow(j + 1);
            InquiryMessageLog inquiryMessageLog = inquiryMessageLogList.get(j);
 
            cell = bodyRow.createCell(0);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(j + 1);
 
            cell = bodyRow.createCell(1);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(inquiryMessageLog.getHospital());
            
            cell = bodyRow.createCell(2);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(inquiryMessageLog.getFromUserPhone());
             
            cell = bodyRow.createCell(3);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(inquiryMessageLog.getFromUserName());
            
            cell = bodyRow.createCell(4);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(inquiryMessageLog.getEachUserCount());
        }
        
		return workBook;
	}
	
	/**
     * 医疗机构每个用户每次使用详情_导出Excel信息
     * @param inquiryMessageLogList
     * @return
     * @throws IOException
     */
	public static XSSFWorkbook exportUseDetailsToExcel(List<InquiryMessageLog> inquiryMessageLogList) throws IOException {
        XSSFWorkbook workBook = new XSSFWorkbook(); // 创建一个workbook 对应一个excel应用文件
        XSSFSheet sheet = workBook.createSheet("Sheet1"); // 在workbook中添加一个sheet,对应Excel文件中的sheet,Sheet名称，可以自定义中文名称
        XSSFRow headRow = sheet.createRow(0); // 构建表头
        XSSFCell cell = null;
        XSSFCellStyle headStyle = getHeadStyle(workBook); //表头样式
        XSSFCellStyle bodyStyle = getBodyStyle(workBook); //表体样式
        XSSFCellStyle dateCellStyle = getDateCellStyle(workBook); //表体日期字段样式
        
        String[] titles = { "序号", "用户手机号码", "用户姓名", "医生手机号码", "医生姓名", "消息内容", "发送时间"};
 
        // 输出标题
        for (int i = 0; i < titles.length; i++) {
            cell = headRow.createCell(i);
            cell.setCellStyle(headStyle);
            cell.setCellValue(titles[i]);
        }
        // 构建表体数据
        for (int j = 0; j < inquiryMessageLogList.size(); j++) {
            XSSFRow bodyRow = sheet.createRow(j + 1);
            InquiryMessageLog inquiryMessageLog = inquiryMessageLogList.get(j);
 
            cell = bodyRow.createCell(0);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(j + 1);
 
            cell = bodyRow.createCell(1);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(inquiryMessageLog.getFromUserPhone());
            
            cell = bodyRow.createCell(2);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(inquiryMessageLog.getFromUserName());
             
            cell = bodyRow.createCell(3);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(inquiryMessageLog.getToUserPhone());
            
            cell = bodyRow.createCell(4);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(inquiryMessageLog.getToUserName());
            
            cell = bodyRow.createCell(5);
            cell.setCellStyle(bodyStyle);
            cell.setCellValue(inquiryMessageLog.getMessagecontent());
            
            cell = bodyRow.createCell(6);
            sheet.setColumnWidth(2, 20 * 256);
            cell.setCellStyle(dateCellStyle);
            cell.setCellValue(inquiryMessageLog.getCreatetime());
        }
        
		return workBook;
	}
	
    /**
     * 设置表头的单元格样式
     * 
     * @return
     */
    public static XSSFCellStyle getHeadStyle(XSSFWorkbook wb) {
        // 创建单元格样式
        XSSFCellStyle cellStyle = wb.createCellStyle();
        // 设置单元格的背景颜色为淡蓝色
        cellStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        cellStyle.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        // 设置单元格居中对齐
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        // 设置单元格垂直居中对齐
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        // 创建单元格内容显示不下时自动换行
        cellStyle.setWrapText(true);
        // 设置单元格字体样式
        XSSFFont font = wb.createFont();
        // 设置字体加粗
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short) 200);
        cellStyle.setFont(font);
        // 设置单元格边框为细线条
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        return cellStyle;
    }
    
    /**
     * 设置表体的单元格样式
     * 
     * @return
     */
    public static XSSFCellStyle getBodyStyle(XSSFWorkbook wb) {
        // 创建单元格样式
        XSSFCellStyle cellStyle = wb.createCellStyle();
        // 设置单元格居中对齐
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        // 设置单元格垂直居中对齐
        cellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        // 创建单元格内容显示不下时自动换行
        cellStyle.setWrapText(true);
        // 设置单元格字体样式
        XSSFFont font = wb.createFont();
        // 设置字体加粗
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short) 200);
        cellStyle.setFont(font);
        // 设置单元格边框为细线条
        cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        return cellStyle;
    }
    
    /**
     * 设置表体的单元格样式
     * 
     * @return
     */
    public static XSSFCellStyle getDateCellStyle(XSSFWorkbook wb) {
        // 创建单元格样式
        XSSFCellStyle dateCellStyle = wb.createCellStyle();
        
        // 设置单元格居中对齐
        dateCellStyle.setAlignment(XSSFCellStyle.ALIGN_CENTER);
        // 设置单元格垂直居中对齐
        dateCellStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);
        // 创建单元格内容显示不下时自动换行
        dateCellStyle.setWrapText(true);
        // 设置单元格字体样式
        XSSFFont font = wb.createFont();
        // 设置字体加粗
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setFontName("宋体");
        font.setFontHeight((short) 200);
        dateCellStyle.setFont(font);
        // 设置单元格边框为细线条
        dateCellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        dateCellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        dateCellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        dateCellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        
        // 设置日期样式
        dateCellStyle.setDataFormat(wb.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
        
        return dateCellStyle;
    }
    
}

