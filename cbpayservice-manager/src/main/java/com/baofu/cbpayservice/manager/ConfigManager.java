package com.baofu.cbpayservice.manager;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 1、配置文件统一管理类
 * </p>
 * ProjectName:cbpay-service
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/7
 */
@Getter
@Component
public class ConfigManager {

    /**
     * 不需要通知清算渠道编号
     */
    @Value("${NOT_NOTIFY_CHANNEL}")
    private String notNotifyChannel;
}
