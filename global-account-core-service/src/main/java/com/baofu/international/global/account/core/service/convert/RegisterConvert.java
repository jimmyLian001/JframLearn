package com.baofu.international.global.account.core.service.convert;

import com.baofu.international.global.account.core.biz.models.AgentRegisterBo;
import com.baofu.international.global.account.core.biz.models.CreateUserBo;
import com.baofu.international.global.account.core.biz.models.SysSecrueqaInfoBo;
import com.baofu.international.global.account.core.common.enums.ErrorCodeEnum;
import com.baofu.international.global.account.core.common.enums.LoginTypeEnum;
import com.baofu.international.global.account.core.common.enums.UserTypeEnum;
import com.baofu.international.global.account.core.common.util.CheckUtil;
import com.baofu.international.global.account.core.facade.model.AgentRegisterReqDto;
import com.baofu.international.global.account.core.facade.model.CreateUserReqDto;
import com.baofu.international.global.account.core.facade.model.RegisterUserReqDto;
import com.baofu.international.global.account.core.facade.model.SysSecrueqaInfoRespDto;
import com.google.common.collect.Lists;
import com.system.commons.exception.BizServiceException;

import java.util.List;

/**
 * description：注册信息转换类
 * <p/>
 *
 * @author : liy on 2017/11/6
 * @version : 1.0.0
 */
public class RegisterConvert {

    private RegisterConvert() {

    }

    /**
     * 系统安全问题
     *
     * @param listBo 请求参数
     * @return 结果集
     */
    public static List<SysSecrueqaInfoRespDto> sysSecrueqaInfoBoConvert(List<SysSecrueqaInfoBo> listBo) {

        List<SysSecrueqaInfoRespDto> listRespDto = Lists.newArrayList();
        for (SysSecrueqaInfoBo infoBo : listBo) {
            SysSecrueqaInfoRespDto infoRespDto = new SysSecrueqaInfoRespDto();
            infoRespDto.setQuestionNo(infoBo.getQuestionNo());
            infoRespDto.setQuestion(infoBo.getQuestion());
            infoRespDto.setQuestionType(infoBo.getQuestionType());
            listRespDto.add(infoRespDto);
        }
        return listRespDto;
    }

    /**
     * 创建个人用户信息转换
     *
     * @param reqDto CreateUserReqDto
     * @return 结果
     */
    public static CreateUserBo toPersonal(CreateUserReqDto reqDto) {

        CreateUserBo bo = new CreateUserBo();
        if (!CheckUtil.isPhone(reqDto.getLoginNo())) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_290003);
        }
        toPublicInfo(bo, reqDto);
        bo.setUserType(UserTypeEnum.PERSONAL.getType());
        bo.setLoginType(LoginTypeEnum.PHONE.getType());
        return bo;
    }

    /**
     * 创建企业用户信息转换
     *
     * @param reqDto CreateUserReqDto
     * @return 结果
     */
    public static CreateUserBo toOrg(CreateUserReqDto reqDto) {

        CreateUserBo bo = new CreateUserBo();
        if (!CheckUtil.isEmail(reqDto.getLoginNo())) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_290003);
        }
        toPublicInfo(bo, reqDto);
        bo.setUserType(UserTypeEnum.ORG.getType());
        bo.setLoginType(LoginTypeEnum.EMAIL.getType());
        return bo;
    }

    /**
     * 公共信息转换
     *
     * @param bo     BO
     * @param reqDto reqDto
     */
    private static void toPublicInfo(CreateUserBo bo, CreateUserReqDto reqDto) {

        bo.setLoginNo(reqDto.getLoginNo());
        bo.setLoginPwd(reqDto.getLoginPwd());
        bo.setQuestionNoOne(reqDto.getQuestionNoOne());
        bo.setAnswerOne(reqDto.getAnswerOne());
        bo.setQuestionNoTwo(reqDto.getQuestionNoTwo());
        bo.setAnswerTwo(reqDto.getAnswerTwo());
        bo.setQuestionNoThree(reqDto.getQuestionNoThree());
        bo.setAnswerThree(reqDto.getAnswerThree());
    }

    /**
     * 校验密码是否相等
     *
     * @param dto 请求参数
     */
    public static void toCheckPwd(RegisterUserReqDto dto) {

        if (!dto.getLoginPwd().equals(dto.getLoginPwdAgain())) {
            throw new BizServiceException(ErrorCodeEnum.ERROR_CODE_190100);
        }
    }

    /**
     * 参数转换
     *
     * @param dto 请求转换参数
     * @return 返回结果
     */
    public static AgentRegisterBo paramConvert(AgentRegisterReqDto dto) {

        AgentRegisterBo agentRegisterBo = new AgentRegisterBo();
        agentRegisterBo.setAgentNo(dto.getAgentNo());
        agentRegisterBo.setUserNo(dto.getUserNo());
        agentRegisterBo.setUserType(dto.getUserType());

        return agentRegisterBo;
    }

}
