package com.baofu.cbpayservice.biz.handler;

import com.baofu.cbpayservice.common.enums.CmdStatusEnums;
import com.baofu.cbpayservice.common.enums.FlagEnum;
import com.baofu.cbpayservice.common.util.StatusUtils;
import com.baofu.cbpayservice.dal.mapper.FiCbPayBizCmdMapper;
import com.baofu.cbpayservice.dal.models.BizCmdDo;
import com.baofu.cbpayservice.manager.CmdManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import com.system.commons.utils.DateUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;

/**
 * 基本命令处理handler虚拟类
 * <p>
 * 1、执行命令
 * 2、错误处理
 * 3、获得执行任务名称
 * 4、执行任务
 * 5、任务执行最后失败信息
 * </p>
 * User: 香克斯 Date: 2016/10/28 ProjectName: cbpayservice-dispatch Version: 1.0
 */
@Slf4j
public abstract class BaseCmdHandler implements CmdHandler, Serializable {

    private static final long serialVersionUID = 4324321787175146951L;

    /**
     * 间隔执行值
     */
    @Setter
    protected int retryInterval = 60;

    /**
     * dispatch命令管理服务
     */
    @Autowired
    private CmdManager cmdManager;

    /**
     * dispatch业务命令Mapper
     */
    @Autowired
    private FiCbPayBizCmdMapper bizCmdMapper;

    /**
     * 1、执行命令
     *
     * @param command 指令
     * @throws Exception 异常
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void execute(BizCmdDo command) throws Exception {

        if (StatusUtils.isCmdEnd(command.getStatus())) {
            return;
        }
        long startTime = System.currentTimeMillis();
        try {
            log.info("任务名称[{}]开始，参数[{}]", getHandlerName(), command);
            //执行dispatch
            doCmd(command);

            //dispatch无异常抛出时更新biz_cmd状态为完成
            success(command);

            log.info("任务名称[{}]执行完成，参数[{}]", getHandlerName(), command);
        } catch (BizServiceException e) {
            log.error("任务名称[{}]异常, 参数[{}], 错误码[{}], 异常信息[{}]", getHandlerName(), command, e.getErrorCode(),
                    e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("任务名称[{}]异常, 参数[{}], 错误码[{}], 异常信息[{}]", getHandlerName(), command,
                    CommonErrorCode.SYSTEM_INNER_ERROR, e.getMessage());
            throw e;
        } finally {
            log.info("队列名称：{},执行耗时时间：{}", getHandlerName(), (System.currentTimeMillis() - startTime));
        }
    }

    /**
     * 2、错误处理
     *
     * @param command    指令
     * @param failReason 失败原因
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void handlerException(BizCmdDo command, String failReason) {
        //获取执行的错误原因，如果有的话
        if (StringUtils.isNotBlank(failReason) && failReason.length() > 512) {
            failReason = failReason.substring(0, 496);
        }
        command.setFailReason(failReason);
        //是否需要重试
        if (needRetry(command)) {
            //重试
            retry(command);
        } else {
            fail(command);
            failedFinally(command);
        }
    }

    /**
     * 3、获得执行任务名称
     *
     * @return 任务名
     */
    protected abstract String getHandlerName();

    /**
     * 4、执行任务
     *
     * @param command 任务
     * @throws Exception 异常
     */
    protected abstract void doCmd(BizCmdDo command) throws Exception;

    /**
     * 5、任务执行最后失败信息
     *
     * @param command 任务
     */
    protected abstract void failedFinally(BizCmdDo command);

    /**
     * 执行成功
     *
     * @param command 指令
     */
    private void success(BizCmdDo command) {

        command.setStatus(CmdStatusEnums.Success.getCode());
        command.setIsDoing(FlagEnum.FALSE.getCode());
        cmdManager.update(command);
    }

    /**
     * 判断是否需重试
     *
     * @param command 指令
     * @return TRUE|FALSE
     */
    private boolean needRetry(BizCmdDo command) {
        if (command.getEnableEndDate() != null &&
                DateUtils.addSeconds(new Date(), retryInterval).after(command.getEnableEndDate())) {
            return false;
        }
        if (command.getMaxRetryTimes() >= 0 && command.getRetryTimes() >= command.getMaxRetryTimes()) {
            return false;
        }
        return true;
    }

    /**
     * 任务重试
     *
     * @param command 指令
     */
    protected void retry(BizCmdDo command) {

        command.setStatus(CmdStatusEnums.Wait.getCode());
        command.setIsDoing(FlagEnum.FALSE.getCode());
        command.setRetryTimes(command.getRetryTimes() + 1);
        command.setNextExeTime(DateUtil.addSeconds(DateUtil.parse(bizCmdMapper.getSysDate(),
                DateUtil.fullPattern), retryInterval));

        cmdManager.update(command);
    }

    /**
     * 任务执行失败，更新状态
     *
     * @param command 任务
     */
    private void fail(BizCmdDo command) {
        command.setStatus(CmdStatusEnums.Failure.getCode());
        command.setIsDoing(FlagEnum.FALSE.getCode());

        cmdManager.update(command);
    }
}