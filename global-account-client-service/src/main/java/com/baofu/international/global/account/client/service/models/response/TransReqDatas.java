package com.baofu.international.global.account.client.service.models.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 功能：内卡反馈代付结果
 * @author: feng_jiang
 * @version : Version:1.0.0 Date : 2017/12/25
 **/
@Setter
@Getter
@XStreamAlias("trans_reqDatas")
public class TransReqDatas<T> {

    /**
     * 为空则不显示
	 */
	private String count;

    /**
     * 返回数据
	 */
	@XStreamImplicit
	private List<T> trans_reqDatas;
}