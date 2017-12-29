package com.baofu.cbpayservice.common.util;

import com.google.common.collect.Maps;
import com.google.common.io.Files;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Excel超大数据读取，抽象Excel2007读取器，excel2007的底层数据结构是xml文件，采用SAX的事件驱动的方法解析
 * xml，需要继承DefaultHandler，在遇到文件内容时，事件会触发，这种做法可以大大降低 内存的耗费，特别使用于大数据量的文件。
 * <p>
 * User: 香克斯 Date:2017/6/6 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
public class ExcelReader extends DefaultHandler {

    private SharedStringsTable sst;

    /**
     * 上一次的内容
     */
    private String lastContents;

    /**
     * 下一次是否String类型
     */
    private boolean nextIsString;

    /**
     * Sheet 编号
     */
    private int sheetIndex = -1;

    /**
     * Excel 每行数据信息
     */
    private Object[] rowData;

    /**
     * 当前行
     */
    private int curRow = 0;

    /**
     * 当前列
     */
    private int curCol = -1;

    private boolean isTElement;

    /**
     * 整个Excel文件内容
     */
    private List<Object[]> dataRowList = new ArrayList<Object[]>();

    /**
     * 定义前一个元素和当前元素的位置，用来计算其中空的单元格数量
     */
    private String preRef = null;

    /**
     * 定义前一个元素和当前元素的位置，用来计算其中空的单元格数量
     */
    private String ref = null;

    /**
     * 总列数
     */
    private int collCount;

    /**
     * 测试方法
     */
    public static void main(String[] args) throws Exception {

        String file = "E://1.xlsx";
        Long startTime = System.currentTimeMillis();
        System.out.println("XML读取数据开始。。。。。。。。。");

        ExcelReader reader = new ExcelReader();
        reader.process(new FileInputStream(file), 1, 17);
        List<Object[]> list = reader.getRowList();
        System.out.println("XML读取总共耗时：" + (System.currentTimeMillis() - startTime) + ",文件总数：" + list.size());
        Map<String, Integer> hashMap = Maps.newHashMap();
        for (int i = 0; i < list.size(); i++) {
            hashMap.put(String.valueOf(list.get(i)[0]), i);
            if (hashMap.get("2017411270764333") != null) {
                System.out.println(hashMap.get("2017411270764333"));
            }
        }
    }

    /**
     * 遍历工作簿中所有的电子表格
     *
     * @param inputStream Excel文件流
     * @throws Exception 抛出异常
     */
    public void process(InputStream inputStream) throws Exception {

        OPCPackage pkg = OPCPackage.open(inputStream);
        XSSFReader r = new XSSFReader(pkg);
        XMLReader parser = XMLReaderFactory.createXMLReader();
        parser.setContentHandler(this);
        sst = r.getSharedStringsTable();

        Iterator<InputStream> sheets = r.getSheetsData();
        while (sheets.hasNext()) {
            curRow = 0;
            sheetIndex++;
            InputStream sheet = null;
            try {
                sheet = sheets.next();
                parser.parse(new InputSource(sheet));
            } finally {
                //关闭文件流
                IOUtils.closeQuietly(sheet);
            }
        }
    }

    /**
     * 只遍历一个电子表格，其中sheetId为要遍历的sheet索引，从1开始，1-3
     *
     * @param inputStream Excel文件流
     * @param sheetId     sheet编号，从1开始
     * @param collCount   Excel最大读取列
     * @throws Exception 抛出异常
     */
    public void process(InputStream inputStream, int sheetId, int collCount) throws Exception {

        OPCPackage pkg = OPCPackage.open(inputStream);
        XSSFReader r = new XSSFReader(pkg);
        XMLReader parser = XMLReaderFactory.createXMLReader();
        parser.setContentHandler(this);
        sst = r.getSharedStringsTable();

        sheetIndex++;
        this.collCount = collCount;
        rowData = new Object[this.collCount];
        // 根据 rId# 或 rSheet# 查找sheet
        InputStream sheet = null;
        try {
            Iterator<InputStream> sheets = r.getSheetsData();
            sheet = sheets.next();
            //读取XML文件
            parser.parse(new InputSource(sheet));
        } finally {
            //关闭文件流
            IOUtils.closeQuietly(sheet);
        }
    }

    /**
     * 读取标签至开始时调用此方法
     *
     * @param uri       地址
     * @param localName 标签名称
     * @param name      标签名称
     * @throws SAXException 抛出异常
     */
    public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {

        // c => 单元格
        if ("c".equals(name)) {
            // 如果下一个元素是 SST 的索引，则将nextIsString标记为true
            nextIsString = ("s".equals(attributes.getValue("t")));
            // 通过getValue可以获取格式信息
            //attributes.getValue("s");
            //前一个单元格的位置
            if (preRef == null) {
                preRef = attributes.getValue("r");
            } else {
                preRef = ref;
            }
            //当前单元格的位置
            ref = attributes.getValue("r");
            curCol++;
            if (curCol < collCount) {
                rowData[curCol] = "";
            }
        }
        // 当元素为t时
        isTElement = ("t".equals(name));
        // 置空
        lastContents = "";
    }

    /**
     * 读取标签至结束时回调此方法
     *
     * @param uri       地址
     * @param localName 标签名称
     * @param name      标签名称
     * @throws SAXException 抛出异常
     */
    public void endElement(String uri, String localName, String name) throws SAXException {

        if (curCol >= collCount && !name.equals("row")) {
            return;
        }
        // 根据SST的索引值的到单元格的真正要存储的字符串
        // 这时characters()方法可能会被调用多次
        if (nextIsString) {
            if (org.apache.commons.lang.StringUtils.isNotBlank(lastContents)) {
                lastContents = new XSSFRichTextString(sst.getEntryAt(Integer.parseInt(lastContents))).toString();
            } else {
                lastContents = "";
            }
        }
        // t元素也包含字符串
        if (isTElement) {
            //每次检测是否前面字符是否为空的单元格，如果是补全单元格内容为空字符串
            excelNullSetting(false);
            rowData[curCol] = lastContents.trim();
            isTElement = false;
            // v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
            // 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
        } else if ("v".equals(name)) {
            String value = lastContents.trim();
            //每次检测是否前面字符是否为空的单元格，如果是补全单元格内容为空字符串
            excelNullSetting(false);
            rowData[curCol] = value;
        } else {
            // 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
            if (name.equals("row")) {
                //每次检测是否前面字符是否为空的单元格，如果是补全单元格内容为空字符串
                excelNullSetting(true);
                //读取到一行结束时，把整行的数据添加到List对象中
                dataRowList.add(rowData);
                rowData = new Object[this.collCount];
                curRow++;
                curCol = -1;
                preRef = null;
                ref = null;
            }
        }
        lastContents = "";
    }

    /**
     * Excel 空列设置成空值
     */
    private void excelNullSetting(boolean isEnd) {

        int len = countNullCell(ref, preRef);
        if (curCol == 0 && len == -1) {
            String xfd = ref.replaceAll("\\d+", "");
            if (!"A".equals(xfd)) {
                rowData[curCol] = "";
                curCol++;
                return;
            }
        }
        //补全单元格之间的空单元格
        if (!isEnd && !StringUtils.equals(ref, preRef)) {
            for (int i = 0; i < len; i++) {
                curCol++;
                rowData[curCol] = "";
            }
            preRef = ref;
        } else if (isEnd) {
            for (int i = curCol + 1; i < collCount; i++) {
                rowData[i] = "";
            }
        }
    }

    /**
     * 获取Excel所有记录信息
     *
     * @return 返回Excel所有记录信息，通过List数组形式
     */
    public List<Object[]> getRowList() {
        return this.dataRowList;
    }

    /**
     * 字符信息转换
     *
     * @param ch     数据字节信息
     * @param start  开始下标
     * @param length 长度
     * @throws SAXException 抛出异常
     */
    public void characters(char[] ch, int start, int length) throws SAXException {
        // 得到单元格内容的值
        lastContents += new String(ch, start, length);
    }

    /**
     * 计算两个单元格之间的单元格数目(同一行)
     *
     * @param ref    当前的Excel坐标位置
     * @param preRef 上一个读取的Excel坐标位置
     * @return 返回两个坐标之间相隔的距离
     */
    private int countNullCell(String ref, String preRef) {
        //excel2007最大行数是1048576，最大列数是16384，最后一列列名是XFD
        String xfd = ref.replaceAll("\\d+", "");
        String xfd_1 = preRef.replaceAll("\\d+", "");

        xfd = fillChar(xfd, 3, '@', true);
        xfd_1 = fillChar(xfd_1, 3, '@', true);

        char[] letter = xfd.toCharArray();
        char[] letter_1 = xfd_1.toCharArray();
        return (letter[0] - letter_1[0]) * 26 * 26 + (letter[1] - letter_1[1]) * 26 + (letter[2] - letter_1[2]) - 1;
    }


    /**
     * 字符串的填充
     *
     * @param str   当前坐标位置的横向坐标位置
     * @param len   长度
     * @param let   靠左移到位置
     * @param isPre
     * @return 返回相加之后字符串
     */
    private String fillChar(String str, int len, char let, boolean isPre) {
        int len_1 = str.length();
        if (len_1 < len) {
            if (isPre) {
                for (int i = 0; i < (len - len_1); i++) {
                    str = let + str;
                }
            } else {
                for (int i = 0; i < (len - len_1); i++) {
                    str = str + let;
                }
            }
        }
        return str;
    }
}
