package com.baofu.cbpayservice.biz.handler.impl;

import com.baofu.cbpayservice.biz.CbPayOrderNotifyBiz;
import com.baofu.cbpayservice.biz.handler.BaseCmdHandler;
import com.baofu.cbpayservice.common.enums.SystemEnum;
import com.baofu.cbpayservice.dal.models.BizCmdDo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 通知商户处理dispatch
 * User: 香克斯 Date:2016/10/28 ProjectName: cbpayservice Version: 1.0
 */
@Slf4j
@Component
public class NotifyMemberDispatch extends BaseCmdHandler {

    /**
     * 人民币订单通知处理
     */
    @Autowired
    private CbPayOrderNotifyBiz cbPayOrderNotifyBiz;

    /**
     * 3、获得执行任务名称
     *
     * @return 任务名
     */
    @Override
    protected String getHandlerName() {

        //设置间隔时间为十分钟
        this.setRetryInterval(600);
        return "通知商户处理dispatch";
    }

    /**
     * 4、执行任务
     *
     * @param command 任务
     * @throws Exception 异常
     */
    @Override
    protected void doCmd(BizCmdDo command) throws Exception {

        cbPayOrderNotifyBiz.notifyMember(Long.parseLong(command.getBizId()), SystemEnum.SYSTEM.getCode());
    }

    /**
     * 5、任务执行最后失败信息
     *
     * @param command 任务
     */
    @Override
    protected void failedFinally(BizCmdDo command) {

        log.error("call 通知商户处理dispatch 处理异常");
    }
}
