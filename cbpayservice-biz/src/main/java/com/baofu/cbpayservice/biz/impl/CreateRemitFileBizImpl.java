package com.baofu.cbpayservice.biz.impl;

import com.baofoo.dfs.client.DfsClient;
import com.baofoo.dfs.client.enums.FileGroup;
import com.baofoo.dfs.client.model.CommandResDTO;
import com.baofoo.dfs.client.model.InsertReqDTO;
import com.baofu.cbpayservice.biz.CreateRemitFileBiz;
import com.baofu.cbpayservice.biz.convert.CreateRemitFileBizConvert;
import com.baofu.cbpayservice.biz.models.*;
import com.baofu.cbpayservice.common.constants.Constants;
import com.baofu.cbpayservice.common.util.JxlsUtils;
import com.baofu.cbpayservice.dal.mapper.FiCbpayFileUploadMapper;
import com.baofu.cbpayservice.dal.models.*;
import com.baofu.cbpayservice.manager.CreateRemitFileManager;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.system.commons.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 创建汇款文件服务
 * <p>
 * 1、创建电商文件
 * 2、创建机票文件
 * 3、创建留学文件
 * 4、创建酒店文件
 * 5、创建旅游文件
 * </p>
 * User: 不良人 Date:2017/10/20 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Service
public class CreateRemitFileBizImpl implements CreateRemitFileBiz {

    /**
     * 创建汇款文件manager
     */
    @Autowired
    private CreateRemitFileManager createRemitFileManager;

    /**
     * 文件批次处理Mapper
     */
    @Autowired
    private FiCbpayFileUploadMapper uploadMapper;

    /**
     * 模板文件路径
     */
    @Value("${settleAmlOrder.upload.templatePath}")
    private String templatePath;

    /**
     * 订单明细文件路径
     */
    @Value("${remittance_download_path}")
    private String uploadPath;

    /**
     * 创建电商文件
     *
     * @param detailBo mq内容
     */
    @Override
    public void electronicCommerce(CreateOrderDetailBo detailBo) {

        try {
            List<ElectronicCommerceDo> queryList = createRemitFileManager.queryElectronicCommerce(detailBo.getFileBatchNo());
            List<ElectronicCommerceBo> ecList = Lists.newArrayList();
            for (ElectronicCommerceDo ec : queryList) {
                ElectronicCommerceBo bo = CreateRemitFileBizConvert.toElectronicCommerceBo(ec);
                ecList.add(bo);
            }
            Map<String, Object> params = Maps.newHashMap();
            params.put("ecList", ecList);
            params.put("memberId", detailBo.getMemberId());
            File template = new File(templatePath + "EC_TEMPLATE.xls");
            File fileOut = new File(uploadPath + detailBo.getFileBatchNo() + Constants.UNDERLINE + template.getName());
            JxlsUtils.exportExcel(template, fileOut, params);
            uploadToDFS(fileOut, detailBo.getFileBatchNo());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建机票文件
     *
     * @param detailBo mq内容
     */
    @Override
    public void airTickets(CreateOrderDetailBo detailBo) {

        try {
            List<AirTicketsDo> queryList = createRemitFileManager.queryAirTickets(detailBo.getFileBatchNo());
            List<AirTicketsBo> atList = Lists.newArrayList();
            for (AirTicketsDo at : queryList) {
                AirTicketsBo bo = CreateRemitFileBizConvert.toAirTicketsBo(at);
                atList.add(bo);
            }
            Map<String, Object> params = Maps.newHashMap();
            params.put("atList", atList);
            params.put("memberId", detailBo.getMemberId());
            File template = new File(templatePath + "AT_TEMPLATE.xls");
            File fileOut = new File(uploadPath + detailBo.getFileBatchNo() + Constants.UNDERLINE + template.getName());
            JxlsUtils.exportExcel(template, fileOut, params);
            uploadToDFS(fileOut, detailBo.getFileBatchNo());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建留学文件
     *
     * @param detailBo mq内容
     */
    @Override
    public void studyAbroad(CreateOrderDetailBo detailBo) {

        try {
            List<StudyAbroadDo> queryList = createRemitFileManager.queryStudyAbroad(detailBo.getFileBatchNo());
            List<RemitStudyAbroadBo> saList = Lists.newArrayList();
            for (StudyAbroadDo studyAbroadDo : queryList) {
                RemitStudyAbroadBo bo = CreateRemitFileBizConvert.toRemitStudyAbroadBo(studyAbroadDo);
                saList.add(bo);
            }
            Map<String, Object> params = Maps.newHashMap();
            params.put("saList", saList);
            params.put("memberId", detailBo.getMemberId());
            File template = new File(templatePath + "SA_TEMPLATE.xls");
            File fileOut = new File(uploadPath + detailBo.getFileBatchNo() + Constants.UNDERLINE + template.getName());
            JxlsUtils.exportExcel(template, fileOut, params);
            uploadToDFS(fileOut, detailBo.getFileBatchNo());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建酒店文件
     *
     * @param detailBo mq内容
     */
    @Override
    public void hotel(CreateOrderDetailBo detailBo) {

        try {
            List<HotelDo> queryList = createRemitFileManager.queryHotel(detailBo.getFileBatchNo());
            List<RemitHotelBo> hotelList = Lists.newArrayList();
            for (HotelDo hotelDo : queryList) {
                RemitHotelBo bo = CreateRemitFileBizConvert.toRemitHotelBo(hotelDo);
                hotelList.add(bo);
            }
            Map<String, Object> params = Maps.newHashMap();
            params.put("hotelList", hotelList);
            params.put("memberId", detailBo.getMemberId());
            File template = new File(templatePath + "HOTEL_TEMPLATE.xls");
            File fileOut = new File(uploadPath + detailBo.getFileBatchNo() + Constants.UNDERLINE + template.getName());
            JxlsUtils.exportExcel(template, fileOut, params);
            uploadToDFS(fileOut, detailBo.getFileBatchNo());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建旅游文件
     *
     * @param detailBo mq内容
     */
    @Override
    public void tourism(CreateOrderDetailBo detailBo) {

        try {
            List<TourismDo> queryList = createRemitFileManager.queryTourism(detailBo.getFileBatchNo());
            List<RemitTourismBo> tourismList = Lists.newArrayList();
            for (TourismDo tourismDo : queryList) {
                RemitTourismBo bo = CreateRemitFileBizConvert.toRemitTourismBo(tourismDo);
                tourismList.add(bo);
            }
            Map<String, Object> params = Maps.newHashMap();
            params.put("tourismList", tourismList);
            params.put("memberId", detailBo.getMemberId());
            File template = new File(templatePath + "TOURISM_TEMPLATE.xls");
            File fileOut = new File(uploadPath + detailBo.getFileBatchNo() + Constants.UNDERLINE + template.getName());
            JxlsUtils.exportExcel(template, fileOut, params);
            uploadToDFS(fileOut, detailBo.getFileBatchNo());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 批量汇款，生成单个文件上传
     *
     * @param file        文件
     * @param fileBatchNo 文件批次号
     */
    private void uploadToDFS(File file, Long fileBatchNo) {
        InsertReqDTO insertReqDTO = new InsertReqDTO();
        insertReqDTO.setOrgCode("CBPAY");
        insertReqDTO.setFileName(fileBatchNo + Constants.UNDERLINE + DateUtil.getCurrent() + ".xls");
        insertReqDTO.setFileGroup(FileGroup.PRODUCT);
        insertReqDTO.setFileDate(DateUtil.getCurrent(DateUtil.fullPatterns));
        insertReqDTO.setRemark("批量汇款，单个文件上传");
        CommandResDTO commandResDTO = DfsClient.upload(file, insertReqDTO);
        log.info("[批量汇款] 生成单个文件上传响应信息:{}", commandResDTO);
        FiCbPayFileUploadDo uploadDo = new FiCbPayFileUploadDo();
        uploadDo.setFileBatchNo(fileBatchNo);
        uploadDo.setDfsFileId(commandResDTO.getFileId());
        uploadMapper.updateByPrimaryKeySelective(uploadDo);
        file.delete();
    }
}
