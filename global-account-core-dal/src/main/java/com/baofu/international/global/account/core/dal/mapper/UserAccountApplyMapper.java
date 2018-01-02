package com.baofu.international.global.account.core.dal.mapper;

import com.baofu.international.global.account.core.dal.model.UserPayeeAccountApplyDo;
import com.baofu.international.global.account.core.dal.model.user.UserApplyAccQueryReqDo;
import com.baofu.international.global.account.core.dal.model.user.UserApplyAccQueryRespDo;

import java.util.List;

/**
 * 收款账户申请开通Mapper服务
 * <p>
 * 1、收款账户申请开通记录信息保存
 * 2、根据申请ID查询用户开户申请信息
 * 3、根据资质编号查询用户开户申请信息
 * 4、根据店铺编号查询用户开户申請信息
 * 5、根据资质、渠道、币种查询用户开户申请信息
 * 6、根据用户号、币种查询用户开户申请信息
 * 7、根据申请编号更新用户申请开户信息
 * </p>
 * ProjectName:account-client
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/5
 */
public interface UserAccountApplyMapper {

    /**
     * 收款账户申请开通记录信息保存
     *
     * @param userPayeeAccountApplyDo 申请信息
     * @return 返回受影响的行数
     */
    int insert(UserPayeeAccountApplyDo userPayeeAccountApplyDo);

    /**
     * 根据申请ID查询用户开户申请信息
     *
     * @param applyId 申请编号
     * @return 返回申请记录信息
     */
    UserPayeeAccountApplyDo selectByApplyId(Long applyId);

    /**
     * 根据资质编号查询用户开户申请信息
     *
     * @param qualifiedNo 资质编号
     * @return 返回申请记录信息集合
     */
    List<UserPayeeAccountApplyDo> selectByQualifiedNo(Long qualifiedNo);

    /**
     * 根据店铺编号查询用户开户申請信息
     *
     * @param storeNo 店铺编号
     * @return 申请账户信息
     */
    UserPayeeAccountApplyDo selectByStoreNo(Long storeNo);

    /**
     * 根据资质、渠道、币种查询用户开户申请信息
     *
     * @param userPayeeAccountApplyDo 条件参数信息
     * @return 申请账户信息
     */
    List<UserPayeeAccountApplyDo> selectByApplyInfo(UserPayeeAccountApplyDo userPayeeAccountApplyDo);

    /**
     * 根据用户号、币种查询用户开户申请信息
     *
     * @param userNo 用户号
     * @param ccy    币种
     * @return 用户开户申请信息
     */
    List<UserPayeeAccountApplyDo> selectByUserNo(Long userNo, String ccy);

    /**
     * 根据申请编号更新用户申请开户信息
     *
     * @param userPayeeAccountApplyDo 更新请求信息
     * @return 返回受影响行数
     */
    int updateStatusByApplyId(UserPayeeAccountApplyDo userPayeeAccountApplyDo);

    /**
     * 用户申请开通账户信息查询
     *
     * @param userApplyAccQueryReqDo 请求查询用户申请开通账户信息条件
     * @return 返回结果，返回结果为List
     */
    List<UserApplyAccQueryRespDo> selectAccApplyList(UserApplyAccQueryReqDo userApplyAccQueryReqDo);

    /**
     * 查询用户申请币种信息
     *
     * @param userNo 用户号
     * @return 币种集合
     */
    List<String> selectAccApplyCcyByUserNo(Long userNo);
}