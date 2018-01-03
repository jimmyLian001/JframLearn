package com.baofu.international.global.account.client.web.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.jxls.common.Context;
import org.jxls.expression.JexlExpressionEvaluator;
import org.jxls.transform.Transformer;
import org.jxls.util.JxlsHelper;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 康志光
 */
@Slf4j
public class JxlsUtils {

    private JxlsUtils() {
    }

    /**
     * 导出excel
     *
     * @param is    模板文件流
     * @param os    输出文件流
     * @param model 内容
     * @throws IOException
     */
    public static void exportExcel(InputStream is, OutputStream os, Map<String, Object> model) throws IOException {
        Context context = new Context();
        if (model != null) {
            for (Map.Entry<String, Object> entry : model.entrySet()) {
                context.putVar(entry.getKey(), entry.getValue());
            }
        }
        JxlsHelper jxlsHelper = JxlsHelper.getInstance();
        Transformer transformer = jxlsHelper.createTransformer(is, os);
        JexlExpressionEvaluator evaluator = (JexlExpressionEvaluator) transformer.getTransformationConfig().getExpressionEvaluator();
        Map<String, Object> funcs = new HashMap<>();
        funcs.put("utils", new JxlsUtils());    //添加自定义功能
        evaluator.getJexlEngine().setFunctions(funcs);
        jxlsHelper.processTemplate(context, transformer);
    }

    /**
     * 导出excel
     *
     * @param xls   模板文件
     * @param out   输出文件
     * @param model 内容
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void exportExcel(File xls, File out, Map<String, Object> model) throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = Files.newInputStream(xls.toPath());
            outputStream = Files.newOutputStream(out.toPath());
            exportExcel(inputStream, outputStream, model);
        } catch (Exception e) {
            log.error("导出文件异常：{}", e);
        } finally {
            IOUtils.closeQuietly(outputStream);
            IOUtils.closeQuietly(inputStream);
        }
    }
}
