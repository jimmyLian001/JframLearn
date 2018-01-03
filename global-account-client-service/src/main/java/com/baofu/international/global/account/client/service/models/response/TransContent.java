package com.baofu.international.global.account.client.service.models.response;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import lombok.Getter;
import lombok.Setter;

/**
 * 功能：内卡反馈代付结果
 * @author: feng_jiang
 * @version : Version:1.0.0 Date : 2017/12/25
 **/
@Setter
@Getter
@XStreamAlias("trans_content")
public class TransContent<T> {

	/**
	 * 反馈内容
	 */
	@XStreamAlias("trans_reqDatas")
	private TransReqDatas<T> trans_reqDatas;

	@XStreamOmitField
	private XStream xStream;

	public TransContent() {
		xStream = new XStream(new DomDriver("UTF-8", new NoNameCoder())) ;
		xStream.autodetectAnnotations(true);
	}

	public String obj2Str(Object obj) {
		return xStream.toXML(obj);
	}

	public Object str2Obj(String str,Class<T> clazz) {
		xStream.alias("trans_content", this.getClass());
		xStream.alias("trans_reqData", clazz);
		return xStream.fromXML(str);
	}
}