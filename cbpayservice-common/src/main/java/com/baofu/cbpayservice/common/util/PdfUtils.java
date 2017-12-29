package com.baofu.cbpayservice.common.util;

import com.google.common.collect.Maps;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;
import com.system.commons.utils.DateUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PDF 相关操作类
 * <p>
 * 1、替换PDF中文本域的值
 * </p>
 * User: 香克斯 Date:2017/7/13 ProjectName: test Version: 1.0
 */
public class PdfUtils {


    /**
     * 替换PDF模版中文本域中的值
     *
     * @param sourcePath 模版文件地址
     * @param value      需要替换的键值对信息
     * @param newPDFPath 新生成的PDF文件地址
     */
    public static boolean editPDF(String sourcePath, Map<String, String> value, String newPDFPath) {

        PdfReader reader;
        FileOutputStream out = null;
        ByteArrayOutputStream bos = null;
        PdfStamper stamper;
        Document doc;
        try {
            if (value == null || value.size() < 1) {
                return Boolean.FALSE;
            }
            out = new FileOutputStream(newPDFPath);// 输出流
            reader = new PdfReader(sourcePath);// 读取pdf模板
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);

            AcroFields form = stamper.getAcroFields();
            BaseFont bf = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            form.addSubstitutionFont(bf);
            for (Map.Entry<String, String> entry : value.entrySet()) {
                if (entry.getValue() == null || "".equals(entry.getValue())) {
                    continue;
                }
                //设置文本域字体
                Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
                Matcher m = p.matcher(entry.getValue());
                if (m.find()) {
                    form.setFieldProperty(entry.getKey(), "textfont", bf, null);
                }
                form.setField(entry.getKey(), entry.getValue());
            }
            // 如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.setFormFlattening(true);
            stamper.close();

            doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();

            return Boolean.TRUE;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOClose(out);
            IOClose(bos);
        }
        return Boolean.FALSE;
    }

    /**
     * IO 异常关闭
     *
     * @param closeable 文件流信息
     */
    private static void IOClose(Closeable closeable) {
        try {
            if (closeable == null) {
                return;
            }
            closeable.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * PDF转化成图片
     *
     * @param sourcePath 模版文件地址
     * @param newPDFPath 新生成的PDF文件地址
     */
    public static void pdfToImage(String sourcePath, String newPDFPath) {

        try {
            File file = new File(sourcePath);
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for (int i = 0; i < pageCount; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 300);
                ImageIO.write(image, "JPG", new File(newPDFPath));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Map<String, String> map = Maps.newHashMap();
        map.put("merchantNo", "12312312");
        map.put("merchantName", "项目上的客户提出一个需求，把政务流程中的表单数据导出成pdf或者图片格式");
        map.put("payeeBankName", "平安银行");
        map.put("payeeBankNo", "622090904");
        map.put("payerNo", "IR123123123");
        map.put("incomeAmt", "10000");
        map.put("incomeRate", "6.8");
        map.put("incomeCnyAmt", "68,000");
        map.put("settleFeeAmt", "1,000");
        map.put("settleCnyAmt", "67,000");
        map.put("settleCnyAmtCHN", "陆万柒仟元整");
        map.put("settleCcy", "美元");
        map.put("settleAmt", "68,000");
        map.put("settleCnyAmtCHN", "陆万柒仟元整");
        map.put("createDate", DateUtil.getCurrent(DateUtil.settlePattern));
        map.put("remarks", "宝付结汇凭证");
        editPDF("D://1.pdf", map, "D://2.pdf");
    }

}