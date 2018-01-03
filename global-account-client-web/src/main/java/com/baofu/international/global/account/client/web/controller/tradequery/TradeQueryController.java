package com.baofu.international.global.account.client.web.controller.tradequery;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.client.common.constant.ConfigDict;
import com.baofu.international.global.account.client.common.constant.NumberDict;
import com.baofu.international.global.account.client.common.constant.NumberStrDict;
import com.baofu.international.global.account.client.common.constant.RequestDict;
import com.baofu.international.global.account.client.common.enums.PlatformEnum;
import com.baofu.international.global.account.client.web.convert.TradeQueryParamConvert;
import com.baofu.international.global.account.client.web.models.SessionVo;
import com.baofu.international.global.account.client.web.models.TradeAccQueryVo;
import com.baofu.international.global.account.client.web.models.TradeQueryVo;
import com.baofu.international.global.account.client.web.models.WithdrawalQueryVo;
import com.baofu.international.global.account.client.web.util.JxlsUtils;
import com.baofu.international.global.account.client.web.util.SessionUtil;
import com.baofu.international.global.account.core.facade.TradeQueryFacade;
import com.baofu.international.global.account.core.facade.model.TUserPayeeAccountDto;
import com.baofu.international.global.account.core.facade.model.TradeAccQueryRespDto;
import com.baofu.international.global.account.core.facade.model.UserStoreInfoRespDto;
import com.baofu.international.global.account.core.facade.model.WithdrawalQueryRespDto;
import com.baofu.international.global.account.core.facade.model.user.PageDataRespDto;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.system.commons.result.Result;
import com.system.commons.utils.DateUtil;
import com.system.commons.utils.JsonUtil;
import com.system.commons.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 交易查询
 *
 * @author 莫小阳  on 2017/11/7.
 */
@Slf4j
@Controller
@RequestMapping("/trade/")
public class TradeQueryController {

    /**
     * 交易查询接口
     */
    @Autowired
    private TradeQueryFacade tradeQueryFacade;

    /**
     * 配置中心
     */
    @Autowired
    private ConfigDict configDict;


    /**
     * 跳转到查询页面  V2
     */
    private static final String TO_TRADE_DETAIL_QUERY_PAGE = "tradeQuery/tradeDetailQuery";

    /**
     * 目标路径
     */
    private static final String TAR_FILE_PATH = "tarFilePath";


    /**
     * EXCEL TYPE  tarFileName
     */
    private static final String XLSX = ".xlsx";

    /**
     * tarFileName
     */
    private static final String TAR_FILE_NAME = "tarFileName";


    /**
     * 跳转到交易查询页面，单纯页面跳转  V2
     *
     * @param modelMap 结果页面
     * @return 结果页面
     */
    @RequestMapping(value = "toTradeDetailQueryPage", method = RequestMethod.GET)
    public String toTradeDetailQueryPage(ModelMap modelMap) {

        log.info("call 跳转到交易查询页面  V2");
        Map<String, String> accTypeMap;
        try {
            SessionVo sessionVo = SessionUtil.getSessionVo();
            accTypeMap = getUserAccInfo(sessionVo);
            modelMap.addAttribute("accTypeList", accTypeMap);
            modelMap.addAttribute(RequestDict.LOGIN_NO, sessionVo.getLoginNo());
        } catch (Exception e) {
            log.error("call 跳转交易查询页面失败 V2，失败信息：{}", e);
        }
        return TO_TRADE_DETAIL_QUERY_PAGE;
    }

    /**
     * 根据用户号和币种查询账户
     *
     * @param ccy 账户类型
     * @return 结果
     */
    @ResponseBody
    @RequestMapping(value = "queryBankAccNoByCcy", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String queryBankAccNoByCcy(String ccy) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        Map map = Maps.newHashMap();
        try {
            SessionVo sessionVo = SessionUtil.getSessionVo();
            log.info("call 根据用户号和币种查询账户开始 参数 userNo：{} accountType：{}", sessionVo.getUserNo(), ccy);
            Result<List<UserStoreInfoRespDto>> listResult = tradeQueryFacade.queryUserStoreByCcy(TradeQueryParamConvert.
                    convertToUserAccInfoReqDto(sessionVo, ccy), MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("call 根据用户号和币种查询账户 返回结果 listResult:{}", listResult);
            ResultUtil.handlerResult(listResult);
            map.put("data", listResult.getResult());
        } catch (Exception e) {
            log.error("call 根据用户号和币种查询账户发生异常，异常信息：{}", e);
        }
        log.info("call 根据用户号和币种查询账户 结束，结果信息：{}", map);
        return JsonUtil.toJSONString(map);
    }


    /**
     * 交易数据查询
     *
     * @param tradeQueryVo 页面参数
     * @return 结果信息
     */
    @ResponseBody
    @RequestMapping(value = "tradeListQuery", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String tradeListQuery(TradeQueryVo tradeQueryVo) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("call 交易查询 条件参数 TradeQueryVo：{}", tradeQueryVo);
        Map map = Maps.newHashMap();
        try {
            //获取用户登录信息
            SessionVo sessionVo = SessionUtil.getSessionVo();
            //查询交易记录
            Result<PageDataRespDto> pageResult = tradeQueryFacade.tradeAccQuery(TradeQueryParamConvert.
                    convert(tradeQueryVo, sessionVo), MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("call 交易查询，返回交易信息 pageResult：{}", pageResult);
            ResultUtil.handlerResult(pageResult);
            PageDataRespDto pageData = pageResult.getResult();
            List<TradeAccQueryVo> list = dealWith(pageData.getResultList());
            pageData.setResultList(list);
            log.info("call 查询交易记录数：{}", pageData.getTotal());
            map.put("pageData", pageData);
        } catch (Exception e) {
            log.error("call 交易数据查询发生异常，异常信息：{}", e);
        }

        return JsonUtil.toJSONString(map);

    }


    /**
     * 提现数据查询
     *
     * @param tradeQueryVo 页面参数
     * @return 结果信息
     */
    @ResponseBody
    @RequestMapping(value = "withdrawalListQuery", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String withdrawalListQuery(TradeQueryVo tradeQueryVo) {

        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("call 提现查询 条件参数 TradeQueryVo：{}", tradeQueryVo);
        Map map = Maps.newHashMap();
        try {
            //获取用户登录信息
            SessionVo sessionVo = SessionUtil.getSessionVo();
            //查询交易记录
            Result<PageDataRespDto> pageDataRst = tradeQueryFacade.withdrawalQuery(TradeQueryParamConvert.
                    convert(tradeQueryVo, sessionVo), MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            log.info("call pageResult = {}", pageDataRst);
            ResultUtil.handlerResult(pageDataRst);
            PageDataRespDto pageData = pageDataRst.getResult();
            List<WithdrawalQueryVo> list = dealWithWithdrawal(pageData.getResultList());
            pageData.setResultList(list);
            log.info("call 查询交易记录数：{}", pageData.getTotal());
            map.put("pageData", pageData);
        } catch (Exception e) {
            log.error("call 提现查询发生异常，异常信息：{}", e);
        }
        return JsonUtil.toJSONString(map);
    }

    /**
     * 导出交易信息 EXCEL
     *
     * @param tradeQueryVo 参数信息
     * @param response     参数信息
     * @param out          参数信息
     * @param type         参数信息
     */
    @RequestMapping(value = "exportExcel", method = RequestMethod.POST)
    public void exportExcel(TradeQueryVo tradeQueryVo, HttpServletResponse response, OutputStream out, String type) {


        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        log.info("call 导出EXCEL 条件参数 TradeQueryVo：{}", tradeQueryVo);

        File file = null;
        InputStream fileInputStream = null;
        Map<String, Object> exportData = Maps.newHashMap();
        try {
            //设置文件形式
            response.setContentType("application/octet-stream; charset=utf-8");
            //获取用户登录信息
            SessionVo sessionVo = SessionUtil.getSessionVo();
            //查询交易记录
            tradeQueryVo.setCurrPageNum(NumberDict.ONE);
            tradeQueryVo.setPageSize(NumberDict.TEN_THOUSAND);
            //获取导出数据信息
            Result<PageDataRespDto> pageResult = getPageResult(tradeQueryVo, sessionVo, type);
            exportData.put("tradeList", pageResult.getResult().getResultList());
            //获取路径信息
            Map<String, String> path = getPath(sessionVo, type);
            //模板文件
            File tempFile = new File(path.get("tempFilePath"), FilenameUtils.getName(path.get("tempFileName")));
            //生成的文件
            File tarFile = new File(path.get(TAR_FILE_PATH), FilenameUtils.getName(path.get(TAR_FILE_NAME)));
            //导出数据
            JxlsUtils.exportExcel(tempFile, tarFile, exportData);
            file = new File(path.get(TAR_FILE_PATH), FilenameUtils.getName(path.get(TAR_FILE_NAME)));
            String fileName = new String(file.getName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            fileInputStream = Files.newInputStream(file.toPath());
            IOUtils.copy(fileInputStream, out);
        } catch (Exception e) {
            log.error("call 导出EXCEL发生异常，异常信息：{}", e);
        } finally {
            IOUtils.closeQuietly(fileInputStream);
            IOUtils.closeQuietly(out);
            if (file != null && file.exists()) {
                try {
                    Files.delete(file.toPath());
                } catch (IOException e) {
                    log.error("call 文件删除失败，{}", e);
                }
                log.info("call 文件删除成功！");
            }
        }
    }

    /**
     * 获取 模板路径和生成的文件路径
     *
     * @param sessionVo 缓存信息
     * @param type      1:交易  2：提现
     * @return 结果
     */
    private Map<String, String> getPath(SessionVo sessionVo, String type) {

        Map<String, String> path = Maps.newHashMap();
        String tempFilePath = "";
        String tarFilePath = "";
        String tempFileName = "";
        String tarFileName = "";
        if (NumberStrDict.ONE.equals(type)) {
            tempFilePath = configDict.getFileTempPath();
            tarFilePath = configDict.getFileCachePath();
            tempFileName = "tradeTmp.xlsx";
            tarFileName = "交易".concat(String.valueOf(sessionVo.getUserNo())).concat(XLSX);
        } else if (NumberStrDict.TWO.equals(type)) {
            tempFilePath = configDict.getFileTempPath();
            tarFilePath = configDict.getFileCachePath();
            tempFileName = "withdrawalTem.xlsx";
            tarFileName = "提现".concat(String.valueOf(sessionVo.getUserNo())).concat(XLSX);
        }
        path.put("tempFilePath", tempFilePath);
        path.put(TAR_FILE_PATH, tarFilePath);
        path.put("tempFileName", tempFileName);
        path.put(TAR_FILE_NAME, tarFileName);

        return path;

    }


    /**
     * 获取用户账户信息
     *
     * @param sessionVo 用户的登录session
     * @return 用户账户信息
     */
    private Map<String, String> getUserAccInfo(SessionVo sessionVo) {

        Map<String, String> result = Maps.newHashMap();
        //查询用户账户信息
        Result<List<TUserPayeeAccountDto>> userAccList = tradeQueryFacade.queryPayeeAccount(sessionVo.getUserNo(),
                null, MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
        ResultUtil.handlerResult(userAccList);
        for (TUserPayeeAccountDto tUserPayeeAccountDto : userAccList.getResult()) {
            //账户状态：0-冻结，1-正常，2-失效
            if (tUserPayeeAccountDto.getStatus() != NumberDict.TWO && StringUtils.isNotBlank(tUserPayeeAccountDto.getCcy())) {
                result.put(tUserPayeeAccountDto.getCcy(), PlatformEnum.getPlatformNameByCcy(tUserPayeeAccountDto.getCcy()));
            }
        }
        log.info("call 平台信息：{} ", result);
        return result;
    }


    /**
     * 获取导出的EXCEL数据
     *
     * @param tradeQueryVo 条件参数信息
     * @param sessionVo    用户缓存信息
     * @param type         1 交易 2：提现
     * @return 结果
     */
    private Result<PageDataRespDto> getPageResult(TradeQueryVo tradeQueryVo, SessionVo sessionVo, String type) {
        Result<PageDataRespDto> pageResult;
        if (NumberStrDict.TWO.equals(type)) {
            //提现查询
            pageResult = tradeQueryFacade.withdrawalQuery(TradeQueryParamConvert.
                    convert(tradeQueryVo, sessionVo), MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            PageDataRespDto result = pageResult.getResult();
            List<WithdrawalQueryVo> arrayList = dealWithWithdrawal(result.getResultList());
            result.setResultList(arrayList);
            pageResult.setResult(result);
        } else {
            // 出入账查询
            pageResult = tradeQueryFacade.tradeAccQuery(TradeQueryParamConvert.
                    convert(tradeQueryVo, sessionVo), MDC.get(MDCPropertyConsts.TRACE_LOG_ID));
            PageDataRespDto result = pageResult.getResult();
            List<TradeAccQueryVo> list = dealWith(result.getResultList());
            result.setResultList(list);
            pageResult.setResult(result);
        }
        ResultUtil.handlerResult(pageResult);

        return pageResult;
    }


    /**
     * 数据交易处理
     *
     * @param resultList 结果数据
     * @return 结果
     */

    private List<TradeAccQueryVo> dealWith(List<TradeAccQueryRespDto> resultList) {
        List<TradeAccQueryVo> list = Lists.newArrayList();
        for (TradeAccQueryRespDto tradeAccQueryRespDto : resultList) {
            TradeAccQueryVo tradeAccQueryVo = new TradeAccQueryVo();
            tradeAccQueryVo.setSerialNumber(this.objToSting(tradeAccQueryRespDto.getSerialNumber()));
            tradeAccQueryVo.setTradeTime(tradeAccQueryRespDto.getTradeTime() == null ? ""
                    : new SimpleDateFormat(DateUtil.settlePattern).format(tradeAccQueryRespDto.getTradeTime()));
            tradeAccQueryVo.setAccountType(objToSting(tradeAccQueryRespDto.getAccountType()));
            tradeAccQueryVo.setAccountNumber(this.objToSting(tradeAccQueryRespDto.getAccountNumber()));
            tradeAccQueryVo.setCcy(this.objToSting(tradeAccQueryRespDto.getCcy()));
            tradeAccQueryVo.setTradeInAccMoney(this.objToSting(tradeAccQueryRespDto.getTradeInAccMoney()));
            tradeAccQueryVo.setTradeOutAccMoney(this.objToSting(tradeAccQueryRespDto.getTradeOutAccMoney()));
            tradeAccQueryVo.setRemark(this.objToSting(tradeAccQueryRespDto.getRemark()));
            list.add(tradeAccQueryVo);
        }
        return list;
    }


    /**
     * 数据提现处理
     *
     * @param resultList 结果数据
     * @return 结果
     */
    private List<WithdrawalQueryVo> dealWithWithdrawal(List<WithdrawalQueryRespDto> resultList) {
        List<WithdrawalQueryVo> list = Lists.newArrayList();
        for (WithdrawalQueryRespDto withdrawalQueryRespDto : resultList) {
            WithdrawalQueryVo withdrawalQueryVo = new WithdrawalQueryVo();
            withdrawalQueryVo.setSerialNumber(this.objToSting(withdrawalQueryRespDto.getSerialNumber()));
            withdrawalQueryVo.setWithdrawalMoney(this.objToBig(withdrawalQueryRespDto.getWithdrawalMoney()));
            withdrawalQueryVo.setWithdrawalFee(this.objToBig(withdrawalQueryRespDto.getWithdrawalFee()));
            withdrawalQueryVo.setTradeRate(this.objToBig(withdrawalQueryRespDto.getTradeRate()));
            withdrawalQueryVo.setSettleMoney(this.objToBig(withdrawalQueryRespDto.getSettleMoney()));
            withdrawalQueryVo.setSettleFee(this.objToBig(withdrawalQueryRespDto.getSettleFee()));
            withdrawalQueryVo.setSucMoney(this.objToBig(withdrawalQueryRespDto.getSucMoney()));
            withdrawalQueryVo.setApplyTime(withdrawalQueryRespDto.getApplyTime() == null
                    ? "" : new SimpleDateFormat(DateUtil.settlePattern).format(withdrawalQueryRespDto.getApplyTime()));
            withdrawalQueryVo.setFinishTime(withdrawalQueryRespDto.getFinishTime() == null
                    ? "" : new SimpleDateFormat(DateUtil.settlePattern).format(withdrawalQueryRespDto.getFinishTime()));
            withdrawalQueryVo.setStoreName(this.objToSting(withdrawalQueryRespDto.getStoreName()));
            withdrawalQueryVo.setBankCard(this.objToSting(withdrawalQueryRespDto.getBankCard()));
            if (StringUtils.isNotBlank(withdrawalQueryRespDto.getState())) {
                if (NumberStrDict.ZERO.equals(withdrawalQueryRespDto.getState()) || NumberStrDict.ONE.equals(withdrawalQueryRespDto.getState())) {
                    withdrawalQueryVo.setState("处理中");
                    withdrawalQueryVo.setClazz("state state-withdrawals");
                } else if (NumberStrDict.TWO.equals(withdrawalQueryRespDto.getState())) {
                    withdrawalQueryVo.setState("成功");
                    withdrawalQueryVo.setClazz("state state-success");
                } else if (NumberStrDict.THREE.equals(withdrawalQueryRespDto.getState())) {
                    withdrawalQueryVo.setState("失败");
                    withdrawalQueryVo.setClazz("state state-fail");
                }
            } else {
                withdrawalQueryVo.setState("");
            }
            list.add(withdrawalQueryVo);
        }
        return list;
    }


    /**
     * OBJ ——》String
     *
     * @param obj 需要转换的对象
     * @return 结果
     */
    private String objToSting(Object obj) {
        if (obj == null) {
            return "";
        } else {
            return String.valueOf(obj);
        }
    }

    /**
     * obj——》BigDecimal
     *
     * @param obj 需要转换的对象
     * @return 结果
     */
    private BigDecimal objToBig(BigDecimal obj) {
        if (obj == null) {
            return BigDecimal.ZERO;
        } else {
            return obj;
        }
    }

}
