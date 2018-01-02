package com.baofu.international.global.account.core.manager.impl;

import com.baofu.international.global.account.core.manager.OrderIdManager;
import com.baofu.international.global.account.core.manager.RedisManager;
import com.system.commons.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 宝付订单号创建
 * <p>
 * </p>
 * User: 香克斯 Date:2017/3/23 ProjectName: cbpayservice Version: 1.0
 */
@Component
public class OrderIdManagerImpl implements OrderIdManager {
    /**
     * 时间格式
     */
    private static final String DATE_PATTERNS = "yyMMddHHmm";
    /**
     * Long类型长度
     */
    private static final int LONG_SIZE = 19;
    /**
     * 生成宝付订单号时redis最大值，当达到最大值时清空redis key
     */
    private static final int REDIS_MAX_VALUE = 999000000;
    /**
     * 宝付订单号redis中生成key
     */
    private static final String REDIS_KEY = "CBPAY:ORDER_ID_CREATE_KEY";
    /**
     * Redis操作实现
     */
    @Autowired
    private RedisManager redisManager;

    /**
     * 字符串左补0
     *
     * @param length 总长度
     * @param str    原字符串
     * @return 左补零后结果
     */
    public static String leftAppendZero(int length, String str) {

        return appendStr(length, Boolean.TRUE, str, "0");
    }

    /**
     * 字符串左补0
     *
     * @param length 总长度
     * @param str    原字符串
     * @return 左补零后结果
     */
    public static String rightAppendZero(int length, String str) {

        return appendStr(length, Boolean.FALSE, str, "0");
    }

    /**
     * 字符串左补0
     *
     * @param length    总长度
     * @param leftBoo   原字符串
     * @param str       原字符串
     * @param appendStr 原字符串
     * @return 左补零后结果
     */
    public static String appendStr(int length, Boolean leftBoo, String str, String appendStr) {

        if (str == null) {
            str = "";
        }
        StringBuilder sb = new StringBuilder();
        if (!leftBoo) {
            sb.append(str);
        }
        while (sb.length() < length) {
            sb.append(appendStr);
        }
        if (leftBoo) {
            sb.append(str);
        }
        return sb.toString();
    }

    /**
     * 创建宝付订单号
     *
     * @return 返回宝付订单号
     */
    @Override
    public Long orderIdCreate() {

        //redis计数取值
        long redisSeq = redisManager.incr(REDIS_KEY);
        if (redisSeq > REDIS_MAX_VALUE) {
            redisManager.deleteObject(REDIS_KEY);
        }
        //当前时间
        String dateTime = DateUtil.getCurrent(DATE_PATTERNS);
        String redisSeqStr = redisSeq + "";

        //返回宝付订单号
        return Long.valueOf(dateTime + leftAppendZero(
                LONG_SIZE - dateTime.length() - redisSeqStr.length(), redisSeqStr));
    }
}
