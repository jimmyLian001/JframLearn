package com.baofu.cbpayservice.biz.convert;

import com.baofoo.Response;
import com.baofoo.dfs.client.DfsClient;
import com.baofoo.dfs.client.enums.Operation;
import com.baofoo.dfs.client.model.CommandResDTO;
import com.baofoo.dfs.client.model.QueryReqDTO;
import com.baofoo.dfs.client.util.SocketUtil;
import com.baofu.cbpayservice.biz.models.ProxyCustomsMqBo;
import com.baofu.cbpayservice.common.constants.NumberConstants;
import com.baofu.cbpayservice.common.util.CSVUtils;
import com.baofu.cbpayservice.common.util.Excelutil;
import com.baofu.cbpayservice.common.util.FileCharsetDetectorUtil;
import com.baofu.cbpayservice.common.util.Txtutil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * 订单汇款(非文件上传订单)biz层参数转换
 * <p>
 * User: 不良人 Date:2017/5/11 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
public final class CbPayOrderRemitBizConvert {

    private CbPayOrderRemitBizConvert() {

    }

    /**
     * 根据文件Id返回excel信息
     *
     * @param proxyCustomsMqBo 请求参数
     * @return 文件数据
     * @throws Exception 异常
     */
    public static List<Object[]> getCommandResDTO(ProxyCustomsMqBo proxyCustomsMqBo) throws Exception {
        QueryReqDTO reqDTO = new QueryReqDTO();
        reqDTO.setFileId(proxyCustomsMqBo.getDfsFileId());
        reqDTO.setOperation(Operation.QUERY);
        Response res = SocketUtil.sendMessage(reqDTO);
        CommandResDTO resDTO = (CommandResDTO) res.getResult();
        byte[] bytes = DfsClient.downloadByte(resDTO.getDfsGroup(), resDTO.getDfsPath());
        InputStream inputStream = new ByteArrayInputStream(bytes);
        String suffix = resDTO.getFileName().substring(resDTO.getFileName().lastIndexOf(".") + 1, resDTO.getFileName().length());

        String charsetName = FileCharsetDetectorUtil.getFileEncoding(new ByteArrayInputStream(bytes));
        log.info("文件字符集编码：{}", charsetName);

        if ("csv".equalsIgnoreCase(suffix)) {
            return CSVUtils.readCsvFile(inputStream, NumberConstants.EIGHTEEN, charsetName);
        } else if ("xls".equals(suffix) || "xlsx".equals(suffix)) {
            return Excelutil.getDataFromExcel(inputStream, 0, 0, 1, resDTO.getFileName());
        } else if ("txt".equals(suffix.toLowerCase())) {
            return Txtutil.getDataFromTxt(inputStream, NumberConstants.EIGHTEEN, resDTO.getFileName());
        }
        return Lists.newArrayList();
    }
}
