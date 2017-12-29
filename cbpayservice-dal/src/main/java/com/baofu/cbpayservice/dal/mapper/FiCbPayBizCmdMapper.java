package com.baofu.cbpayservice.dal.mapper;


import com.baofu.cbpayservice.dal.models.BizCmdDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * dispatch信息底层操作
 * <p>
 * 1、批量新增命令
 * 2、新增命令
 * 3、更新命令
 * 4、查询待处理命令
 * 5、根据业务号,业务类型选取用于分析的数据
 * 6、重新激活某个时间点前的所有执行中命令
 * 7、获取序列
 * 8、获取数据库时间
 * 9、分页获取业务处理命令
 * 10、获取业务处理命令的总值
 * 11、依据ID获取业务处理命令
 * 12、批量更新业务处理命令
 * 13、更新业务命令
 * 14、更新状态
 * </p>
 * User: 香克斯  Date: 2016/10/28 ProjectName: cbpayservice-dispatch Version: 1.0
 */
public interface FiCbPayBizCmdMapper {

    /**
     * 新增命令
     *
     * @param bizCmdDO 请求参数
     * @return 是否成功
     */
    int insert(BizCmdDo bizCmdDO);

    /**
     * 更新命令
     *
     * @param bizCmdDO 请求参数
     */
    int update(BizCmdDo bizCmdDO);

    /**
     * 查询待处理命令
     *
     * @param bizType 任务类型
     * @param cmdNum  命令数量
     * @return List集合
     */
    List<BizCmdDo> selectToDoCmdList(@Param("bizType") String bizType,
                                     @Param("cmdNum") int cmdNum,
                                     @Param("envTag") String envTag);


    /**
     * 重新激活某个时间点前的所有执行中命令
     */
    int reactiveCommandsServerIP(@Param("serverIP") String serverIP);

    /**
     * 获取数据库时间
     *
     * @return 数据时间
     */
    String getSysDate();

    /**
     * 分页获取业务处理命令
     *
     * @param bizCmdDO 请求对象
     * @return List
     */
    List<BizCmdDo> selectBizCmdPage(@Param("bean") BizCmdDo bizCmdDO,
                                    @Param("startRow") Integer startRow,
                                    @Param("endRow") Integer endRow);

    /**
     * 获取业务处理命令的总值
     *
     * @param bizCmdDO 请求对象
     * @return List
     */
    int selectBizCmdCount(@Param("bean") BizCmdDo bizCmdDO);

    /**
     * 更新业务命令
     *
     * @param bizCmdDO 更新参数
     */
    Integer updateBizCmd(BizCmdDo bizCmdDO);

    /**
     * 更新状态
     *
     * @param serverIP IP地址
     * @param id       ID
     * @return 返回信息
     */
    int updateBizCmdStatus(@Param("id") Long id, @Param("serverIP") String serverIP);
}
