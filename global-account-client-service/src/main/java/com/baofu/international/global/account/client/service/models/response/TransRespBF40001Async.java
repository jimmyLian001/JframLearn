package com.baofu.international.global.account.client.service.models.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;

/**
 * 代付交易  异步返回
 * @author Administrator
 *
 */
@XStreamAlias("trans_reqData")
@Setter
@Getter
public class TransRespBF40001Async {

    /**
     * 宝付订单号
	 */
	private String trans_orderid;

	/**
	 *宝付批次号
	 */
    private String trans_batchid;

	/**
	 *商户订单号
	 */
	private String trans_no;

	/**
	 *交易金额
	 */
	private String trans_money;

	/**
	 *收款人姓名
	 */
	private String to_acc_name;

	/**
	 *收款人银行帐号
	 */
	private String to_acc_no;

	/**
	 *交易手续费
	 */
	private String trans_fee;

	/**
	 *交易处理状态
	 */
	private String state;

	/**
	 *备注（错误信息）
	 */
	private String trans_remark;

	/**
	 *交易申请时间
	 */
	private String trans_starttime;

	/**
	 *交易申请时间
	 */
	private String trans_endtime;

	@Override
	public String toString() {
		return "TransRespBF40001Async [trans_orderid=" + trans_orderid + ", trans_batchid=" + trans_batchid
				+ ", trans_no=" + trans_no + ", trans_money=" + trans_money + ", to_acc_name=" + to_acc_name
				+ ", to_acc_no=" + to_acc_no + ", trans_fee=" + trans_fee + ", state=" + state + ", trans_remark="
				+ trans_remark + ", trans_starttime=" + trans_starttime + ", trans_endtime=" + trans_endtime + "]";
	}
}
