package com.baofu.cbpayservice.manager;


import com.baofu.cbpayservice.dal.models.BizCmdDo;

import java.util.List;

/**
 * 异步组件业务处理接口
 * <p>
 * 1、查询待处理命令
 * 2、取待处理的命令数量
 * 3、更新命令
 * 4、添加命令
 * 5、激活命令
 * </p>
 * User: 香克斯 Date: 2016/07/06 ProjectName: system-dispatch Version: 1.0
 */
public interface CmdManager {

    /**
     * 1、查询待处理命令
     *
     * @param bizType 业务类型
     * @param cmdNum  查询返回数
     * @return 待处理命令集合
     */
    List<BizCmdDo> lockAndListCommands(String bizType, int cmdNum);

    /**
     * 2、添加命令
     *
     * @param cmdObject 待处理命令
     */
    void insert(BizCmdDo cmdObject);

    /**
     * 3、更新命令
     *
     * @param cmdObject 命令
     */
    void update(BizCmdDo cmdObject);

    /**
     * 4、激活命令
     *
     * @param serverIP 服务ip
     * @return 返回信息
     */
    int reactiveCommandsServerIP(String serverIP);

    /**
     * 5、手动释放业务锁
     *
     * @param bizType 锁名
     */
    void releaseLock(String bizType);
}
