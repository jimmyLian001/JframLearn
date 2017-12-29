package com.baofu.cbpayservice.biz.freemarker;


import com.alibaba.fastjson.util.IOUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by 康志光 on 2016/11/11.
 */
@Slf4j
public class FreemarkTemplate {

    private final static FreemarkTemplate freemarkTemplate = new FreemarkTemplate();

    private FreemarkTemplate() {
    }

    public static FreemarkTemplate getInstance() {
        return freemarkTemplate;
    }

    /**
     * 根据模版生成文件
     *
     * @param datas
     * @param srcHomePath
     * @param tempFilePath
     * @param desFilePath
     */
    public void exportToFile(Object datas, String srcHomePath, String tempFilePath, String desFilePath) {

        Writer out = null;
        try {

            Configuration config = new Configuration();
            config.setDirectoryForTemplateLoading(new File(srcHomePath));
            config.setEncoding(Locale.CHINA, "UTF-8");
            Template template = config.getTemplate(tempFilePath);
            out = new OutputStreamWriter(new FileOutputStream(desFilePath));

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("list", datas);
            template.process(params, out);
            out.flush();
        } catch (Exception e) {
            log.error("文件解析异常", e);
        } finally {
            IOUtils.close(out);
        }
    }
}
