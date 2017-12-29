package com.baofu.cbpayservice.common.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述
 * <p>
 * </p>
 * User: 不良人 Date:2017/1/6 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
public class Excelutil {
    private static POIFSFileSystem fs;

    /**
     * 读取excel中的数据 返回一个list
     *
     * @param inputStream 文件流
     * @param startLine   从第几行开始读取
     * @param sheetNum    从第几列开始读取
     * @return 读取之后List
     * @throws Exception
     */
    public static List<Object[]> getDataFromExcel(InputStream inputStream, int startLine, int sheetNum, Integer collCount, String fileName) {
        List<Object[]> list = Lists.newArrayList();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        try {
            if ("xls".equals(suffix)) {
                list = getDataFromExcel03(inputStream, startLine, sheetNum, collCount, null);
            } else if ("xlsx".equals(suffix)) {
//                list = getDataFromExcel07(inputStream, startLine, sheetNum, collCount, null);
                ExcelReader reader = new ExcelReader();
                reader.process(inputStream, sheetNum + 1, collCount);
                list = reader.getRowList();
            }
        } catch (Exception e) {
            log.info("解析Excel失败，异常信息 ——> {}", e);
        }
        return list;
    }

    /**
     * 读取excel中的数据 包含空行 返回一个list
     *
     * @param inputStream 文件流
     * @param startLine   从第几行开始读取
     * @param sheetNum    从第几列开始读取
     * @return 读取之后List
     * @throws Exception
     */
    public static List<Object[]> getDataFromExcelVerify(InputStream inputStream, int startLine, int sheetNum, Integer collCount, String fileName) {
        List<Object[]> list = Lists.newArrayList();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        try {
            if ("xls".equals(suffix)) {
                list = getDataFromExcel03(inputStream, startLine, sheetNum, collCount, null);
            } else if ("xlsx".equals(suffix)) {
//                list = getDataFromExcel07(inputStream, startLine, sheetNum, collCount, null);
                ExcelReader reader = new ExcelReader();
                reader.process(inputStream, sheetNum + 1, collCount);
                list = reader.getRowList();
            }
        } catch (Exception e) {
            log.info("解析Excel失败，异常信息 ——> {}", e);
        }
        return list;
    }


    /**
     * 读取excel中的数据 返回一个list
     *
     * @param file      文件
     * @param startLine 从第几行开始读取
     * @param sheetNum  从第几列开始读取
     * @return 读取之后List
     * @throws Exception
     */
    public static List<Object[]> getDataFromExcel(File file, int startLine, int sheetNum, Integer collCount, String fileName) {
        List<Object[]> list = Lists.newArrayList();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
        try {
            InputStream inputStream = new FileInputStream(file);
            if ("xls".equals(suffix)) {
                list = getDataFromExcel03(inputStream, startLine, sheetNum, collCount, null);
            } else if ("xlsx".equals(suffix)) {
//                list = getDataFromExcel07(inputStream, startLine, sheetNum, collCount, null);
                ExcelReader reader = new ExcelReader();
                reader.process(inputStream, sheetNum + 1, collCount);
                list = reader.getRowList();
            }
        } catch (Exception e) {
            log.info("解析Excel失败，异常信息 ——> {}", e);
        }
        return list;
    }


    /**
     * 读取excel数据 适用excel2007
     *
     * @param inputStream 文件流
     * @param startLine   读取开始行数
     * @param sheetNum    读取开始的Sheet
     * @param collCount   总列数
     * @param lineCount   总行数
     * @return List集合
     * @throws Exception
     */
    public static List<Object[]> getDataFromExcel07(InputStream inputStream, Integer startLine, Integer sheetNum, Integer collCount, Integer lineCount) {
        StringBuffer rowStr = new StringBuffer();
        ArrayList<Object[]> arrayList = new ArrayList<Object[]>();
        if (inputStream == null) {
            return arrayList;
        }
        try {
            XSSFWorkbook wb = new XSSFWorkbook(inputStream);
            sheetNum = sheetNum == null ? 0 : sheetNum;
            XSSFSheet xssfsheet = wb.getSheetAt(sheetNum);
            //判断传入的行数是否为空，如果为空，系统自动获取Excel中的行数
            lineCount = lineCount == null ? xssfsheet.getPhysicalNumberOfRows() : lineCount;
            for (int j = 0; j < lineCount; j++) {
                //比较和传入的开始读取行数是否符合
                if (j < startLine) {
                    continue;
                }
                //开始循环行数
                XSSFRow xssfrow = xssfsheet.getRow(j);
                if (xssfrow == null) {
                    arrayList.add(new Object[collCount]);
                    continue;
                }
                //传入的列数如果为空，系统自动获取列数
                collCount = collCount == null ? xssfrow.getLastCellNum() : collCount;
                if (collCount < 0) {
                    continue;
                }
                rowStr.delete(0, rowStr.length());
                Object[] objs = new Object[collCount];
                for (int k = 0; k < collCount; k++) {
                    XSSFCell xssfcell = xssfrow.getCell((short) k);
                    if (xssfcell != null) {
                        switch (xssfcell.getCellType()) {
                            case 0:
                                String value = xssfcell.toString();
                                BigDecimal d = new BigDecimal(String.valueOf(xssfcell.getNumericCellValue()));
                                if (!value.contains(".0")) {
                                    d = d.stripTrailingZeros();
                                }
                                objs[k] = d.toPlainString();
                                break;
                            case 1:
                                objs[k] = xssfcell.getStringCellValue().trim();
                                break;
                            case 2:
                                objs[k] = xssfcell.getCellFormula().trim();
                                break;
                            case 3:
                                objs[k] = "";
                                break;
                            case 4:
                                objs[k] = xssfcell.getBooleanCellValue();
                                break;
                            case 5:
                                objs[k] = xssfcell.getErrorCellValue();
                                break;
                            default:
                                objs[k] = "";
                                break;
                        }
                    } else {
                        objs[k] = "";
                    }
                    rowStr.append(objs[k]);
                }
                if (rowStr.length() > 0) {
                    arrayList.add(objs);
                }
            }
        } catch (Exception e) {
            log.info("解析Excel失败，异常信息 ——> {}", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return arrayList;
    }

    /**
     * 读取excel数据 适用excel2003
     *
     * @param inputStream 文件流
     * @param startLine   读取开始行数
     * @param sheetNum    读取开始的Sheet
     * @param collCount   总列数
     * @param lineCount   总行数
     * @return List集合
     * @throws Exception
     */
    public static List<Object[]> getDataFromExcel03(InputStream inputStream, Integer startLine, Integer sheetNum, Integer collCount, Integer lineCount) {
        ArrayList<Object[]> arrayList = new ArrayList<Object[]>();
        StringBuffer rowStr = new StringBuffer();
        try {
            fs = new POIFSFileSystem(inputStream);
            HSSFWorkbook hssfworkbook = new HSSFWorkbook(fs);
            sheetNum = sheetNum == null ? 0 : sheetNum;
            HSSFSheet hssfsheet = hssfworkbook.getSheetAt(sheetNum);
            //判断传入的行数是否为空，如果为空，系统自动获取Excel中的行数
            lineCount = lineCount == null ? hssfsheet.getPhysicalNumberOfRows() : lineCount;
            for (int j = 0; j < lineCount; j++) {
                if (j < startLine) {
                    continue;
                }
                HSSFRow hssfrow = hssfsheet.getRow(j);
                if (hssfrow == null) {
                    arrayList.add(new Object[collCount]);
                    continue;
                }
                //传入的列数如果为空，系统自动获取列数
                collCount = collCount == null ? hssfrow.getLastCellNum() : collCount;
                if (collCount < 0) {
                    continue;
                }
                rowStr.delete(0, rowStr.length());
                Object[] objs = new Object[collCount];
                for (int k = 0; k < collCount; k++) {
                    HSSFCell hssfcell = hssfrow.getCell(k);
                    if (hssfcell != null) {
                        switch (hssfcell.getCellType()) {
                            case 0:
                                String value = hssfcell.toString();
                                BigDecimal d = new BigDecimal(String.valueOf(hssfcell.getNumericCellValue()));
                                if (!value.contains(".0")) {
                                    d = d.stripTrailingZeros();
                                }
                                objs[k] = d.toPlainString();
                                break;
                            case 1:
                                objs[k] = hssfcell.getRichStringCellValue().getString().trim();
                                break;
                            case 2:
                                objs[k] = hssfcell.getCellFormula().trim();
                                break;
                            case 3:
                                objs[k] = "";
                                break;
                            case 4:
                                objs[k] = hssfcell.getBooleanCellValue();
                                break;
                            case 5:
                                objs[k] = hssfcell.getErrorCellValue();
                                break;
                            default:
                                objs[k] = "";
                                break;
                        }
                    } else {
                        objs[k] = "";
                    }
                    rowStr.append(objs[k]);
                }
                arrayList.add(objs);
             /*   if (rowStr.length()>0) {
                    arrayList.add(objs);
                }*/
            }
        } catch (Exception e) {
            log.info("解析Excel失败，异常信息 ——> {}", e);
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
        return arrayList;
    }
}
