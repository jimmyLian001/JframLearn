package com.baofu.international.global.account.core.service.impl.user;

import com.baofu.global.cross.border.logback.ex.constant.MDCPropertyConsts;
import com.baofu.international.global.account.core.biz.MqSendService;
import com.baofu.international.global.account.core.biz.models.UserApplyAccQueryReqBo;
import com.baofu.international.global.account.core.common.constant.*;
import com.baofu.international.global.account.core.common.enums.MqSendQueueNameEnum;
import com.baofu.international.global.account.core.common.util.BeanCopyUtils;
import com.baofu.international.global.account.core.dal.mapper.UserAccountApplyMapper;
import com.baofu.international.global.account.core.dal.model.user.UserApplyAccQueryReqDo;
import com.baofu.international.global.account.core.dal.model.user.UserApplyAccQueryRespDo;
import com.baofu.international.global.account.core.facade.model.res.UserApplyAccQueryRespDto;
import com.baofu.international.global.account.core.facade.model.user.UserApplyAccQueryReqDto;
import com.baofu.international.global.account.core.facade.user.UserApplyAccQueryFacade;
import com.baofu.international.global.account.core.manager.OrderIdManager;
import com.baofu.international.global.account.core.manager.RedisManager;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.system.commons.result.PageRespDTO;
import com.system.commons.result.Result;
import com.system.commons.utils.ExceptionUtils;
import com.system.commons.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 用户账户申请开通查询相关操作
 * <p>
 * 1、查詢用戶申請開通賬戶信息
 * 2、下载用戶申請開通賬戶信息
 * </p>
 * ProjectName:global-account-core
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/17
 */
@Slf4j
@Service
public class UserApplyAccQueryServiceImpl implements UserApplyAccQueryFacade {

    /**
     * 用户申请开通账户Mapper操作类
     */
    @Autowired
    private UserAccountApplyMapper userAccountApplyMapper;


    /**
     * Redis管理
     */
    @Autowired
    private RedisManager redisManager;


    /**
     * MQ 服务类
     */
    @Autowired
    private MqSendService mqSendService;

    /**
     * 记录编号生成服务
     */
    @Autowired
    private OrderIdManager orderIdManager;


    /**
     * 查詢用戶申請開通賬戶信息
     *
     * @param queryReqDto 查詢條件
     * @param traceLogId  日志ID
     * @return 返回結果
     */
    @Override
    public Result<PageRespDTO<UserApplyAccQueryRespDto>> queryUserApplyAcc(UserApplyAccQueryReqDto queryReqDto, String traceLogId) {

        Result<PageRespDTO<UserApplyAccQueryRespDto>> result;
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        try {
            log.info("查詢用戶申請開通賬戶信息,请求参数：{}", queryReqDto);
            UserApplyAccQueryReqDo userApplyAccQueryReqDo = BeanCopyUtils.objectConvert(queryReqDto, UserApplyAccQueryReqDo.class);
            //使用分頁插件分页查询
            PageHelper.offsetPage(queryReqDto.getStartRow(), queryReqDto.getPageSize());

            //查詢結果
            Page<UserApplyAccQueryRespDo> pageDate = (Page<UserApplyAccQueryRespDo>) userAccountApplyMapper.selectAccApplyList(userApplyAccQueryReqDo);

            //转换结果集之后并封装返回结果信息
            result = new Result<>(new PageRespDTO<>(Integer.valueOf(pageDate.getTotal() + ""),
                    queryReqDto.getPageSize(), BeanCopyUtils.listConvert(pageDate.getResult(), UserApplyAccQueryRespDto.class)));
        } catch (Exception e) {
            log.error("查詢用戶申請開通賬戶信息异常：", e);
            result = ExceptionUtils.getResponse(e);
        }
        log.info("查詢用戶申請開通賬戶信息，返回结果：{}", result);
        return result;
    }


    /**
     * 异步下载用户申请开通账户信息申请接口
     *
     * @param userApplyAccQueryReqDto 用户申请信息
     * @param traceLogId              日志ID
     * @return 结果
     */
    @Override
    public Result<Long> downloadUserDataApply(UserApplyAccQueryReqDto userApplyAccQueryReqDto, String traceLogId) {
        Result<Long> result;
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        try {
            log.info("call 异步下载用户申请开通账户信息申请接口,请求参数：{}", userApplyAccQueryReqDto);
            Long recordId = orderIdManager.orderIdCreate();
            UserApplyAccQueryReqBo userApplyAccQueryReqBo = BeanCopyUtils.objectConvert(userApplyAccQueryReqDto, UserApplyAccQueryReqBo.class);
            userApplyAccQueryReqBo.setRecordId(recordId);
            //调用下载数据队列MQ信息
            mqSendService.sendMessage(MqSendQueueNameEnum.SKYEE_USER_ACC_APPLY_DATA_ZIP_EXPORT, JsonUtil.toJSONString(userApplyAccQueryReqBo));
            log.info("call 下载账户申请信息MQ，生产者、队列名：{},内容：{}",
                    MqSendQueueNameEnum.SKYEE_USER_ACC_APPLY_DATA_ZIP_EXPORT, userApplyAccQueryReqBo);
            result = new Result<>(recordId);
        } catch (Exception e) {
            log.error("call Skye 申请下载用户开户信息异常：", e);
            result = ExceptionUtils.getResponse(e);
        }
        return result;
    }

    /**
     * @param recordId   申请记录编号ID
     * @param traceLogId 日志ID
     * @return DFS id
     */
    @Override
    public Result<Long> queryDfsIdByRecordId(Long recordId, String traceLogId) {

        Result<Long> result;
        MDC.put(MDCPropertyConsts.TRACE_LOG_ID, traceLogId);
        try {
            log.info("call 根据下载申请记录编号获取DFS编号，申请记录编号：recordId = {}：", recordId);
            String dfsId = redisManager.queryObjectByKey(String.valueOf(recordId));
            if (StringUtils.isEmpty(dfsId)) {
                dfsId = NumberStrDict.ZERO;
            }
            result = new Result<>(Long.valueOf(dfsId));
            if (!StringUtils.isEmpty(dfsId) && !NumberStrDict.ZERO.equals(dfsId)) {
                //成功获取后将数据从redis删除
                redisManager.deleteObject(String.valueOf(recordId));
                log.info("call DFS ID获取成功，将记录从Redis中删除成功！");
            }
        } catch (Exception e) {
            log.error("call Skye 根据申请编号获取DFS id：", e);
            result = ExceptionUtils.getResponse(e);
        }
        return result;
    }


}
