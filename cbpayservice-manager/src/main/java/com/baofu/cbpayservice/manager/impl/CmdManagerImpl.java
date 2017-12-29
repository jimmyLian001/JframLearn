package com.baofu.cbpayservice.manager.impl;

import com.baofu.cbpayservice.common.enums.CmdStatusEnums;
import com.baofu.cbpayservice.common.enums.FlagEnum;
import com.baofu.cbpayservice.dal.mapper.FiCbPayBizCmdMapper;
import com.baofu.cbpayservice.dal.models.BizCmdDo;
import com.baofu.cbpayservice.manager.BizLockManager;
import com.baofu.cbpayservice.manager.CmdManager;
import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import com.system.ext.logback.util.IPUtil;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 异步组件业务处理接口实现
 * <p>
 * 1、查询待处理命令
 * 2、取待处理的命令数量
 * 3、更新命令
 * 4、添加命令
 * 5、激活命令
 * </p>
 * User: 香克斯 Date: 2016/10/28 ProjectName: cbpayservice-dispatch Version: 1.0
 */
@Slf4j
@Service
public class CmdManagerImpl implements CmdManager {

    /**
     * 业务命令锁处理服务
     */
    @Autowired
    private BizLockManager bizLockManager;

    /**
     * 命令数据库处理服务
     */
    @Autowired
    private FiCbPayBizCmdMapper bizCmdMapper;

    /**
     * 环境标签
     */
    @Setter
    private String envTag;

    /**
     * 默认锁定时间
     */
    @Setter
    private Long defaultLockTime = 3 * 60 * 1000L;

    /**
     * 返回待处理的命令
     *
     * @param bizType 业务类型
     * @param cmdNum  查询返回数
     * @return 待处理命令集合
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<BizCmdDo> lockAndListCommands(final String bizType, final int cmdNum) {

        String lockName = envTag + "." + bizType;
        log.info("call BaseCmdManager lockAndListCommands 队列名称：{},队列条数：{}", lockName, cmdNum);
        Boolean lock = Boolean.FALSE;
        try {
            lock = bizLockManager.getLockWaiting(lockName, defaultLockTime);
            if (!lock) {
                log.warn("抢锁失败：{}，{}", bizType, cmdNum);
                return null;
            }

            Long startTime = System.currentTimeMillis();
            log.info("call BaseCmdManager queryToDoCmdList 队列名称：{},队列数量：{}", bizType, cmdNum);
            List<BizCmdDo> bizCmds = new ArrayList<BizCmdDo>();

            List<BizCmdDo> bizCmdDOs = bizCmdMapper.selectToDoCmdList(bizType, cmdNum, envTag);
            log.info("call bizCmdMapper selectToDoCmdList Result Size :{}", bizCmdDOs.size());
            String serverIp = IPUtil.getServerIp();
            //查询带有环境标签的命令
            for (BizCmdDo cmd : bizCmdDOs) {

                int result = bizCmdMapper.updateBizCmdStatus(cmd.getId(), serverIp);
                if (result == 1) {
                    cmd.setServerIP(serverIp);
                    bizCmds.add(cmd);
                }
            }
            log.info("队列名称:{},条数：{},耗时：{}", bizType, bizCmds.size(), System.currentTimeMillis() - startTime);
            return bizCmds;
        } finally {
            if (lock) {
                bizLockManager.releaseLock(lockName);
            }
        }

    }

    /**
     * 更新命令
     *
     * @param cmdObject 命令
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void update(BizCmdDo cmdObject) {

        try {

            bizCmdMapper.update(cmdObject);

        } catch (Exception e) {

            String message = "更新待处理命令异常[" + cmdObject.toString() + "]";
            log.error(message, e);
            throw new BizServiceException(CommonErrorCode.DATA_BASE_ERROR, message, e);
        }
    }

    /**
     * 添加命令
     *
     * @param cmdObject 待处理命令
     */
    @Override
    public void insert(BizCmdDo cmdObject) throws RuntimeException {

        if (cmdObject == null) {
            throw new BizServiceException(CommonErrorCode.NULL_IS_ILLEGAL_PARAM, "插入命令信息为空");
        }
        //命令添加环境标签
        cmdObject.setEnvTag(envTag);
        cmdObject.setStatus(CmdStatusEnums.Initial.getCode());
        cmdObject.setIsDoing(FlagEnum.FALSE.getCode());
        //添加一条命令
        bizCmdMapper.insert(cmdObject);
    }

    /**
     * 激活命令
     *
     * @param serverIp 服务IP
     * @return int
     */
    @Override
    public int reactiveCommandsServerIP(String serverIp) {
        return bizCmdMapper.reactiveCommandsServerIP(serverIp);
    }

    /**
     * 5、手动释放业务锁
     *
     * @param bizType 锁名
     */
    @Override
    public void releaseLock(String bizType) {

        String lockName = envTag + "." + bizType;

        bizLockManager.releaseLock(lockName);
    }
}
