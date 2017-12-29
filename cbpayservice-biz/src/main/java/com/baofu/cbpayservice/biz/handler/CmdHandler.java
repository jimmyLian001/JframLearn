package com.baofu.cbpayservice.biz.handler;


import com.baofu.cbpayservice.dal.models.BizCmdDo;

/**
 * 命令执行具体业务的CmdHandler
 * <p>
 * 1、处理具体命令
 * 2、任务执行失败的异常处理
 * </p>
 * User: 香克斯 Date: 2016/07/06 ProjectName: system-dispatch Version: 1.0
 */
public interface CmdHandler {

    /**
     * 1、处理具体命令
     *
     * @param command 命令
     * @throws Exception
     */
    void execute(BizCmdDo command) throws Exception;

    /**
     * 2、任务执行失败的异常处理
     *
     * @param command    命令
     * @param failReason 失败原因
     */
    void handlerException(BizCmdDo command, String failReason);
}
