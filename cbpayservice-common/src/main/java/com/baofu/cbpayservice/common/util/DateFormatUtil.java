package com.baofu.cbpayservice.common.util;

import com.baofu.cbpayservice.common.util.other.FormatUtil;
import com.system.commons.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理工具类
 * <p>
 * 1、获取前一天的日期
 * </p>
 * User: wanght Date: 2017/03/08 ProjectName: cbpayservice-common Version: 1.0
 */
public class DateFormatUtil {
    /**
     * 获取前一天的日期
     *
     * @param date 日期
     * @return 返回前一天的日期  yyyy-MM-dd
     */
    public static String getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
    }


    /**
     * 获取前一天的日期
     *
     * @param date 日期
     * @return 返回前一天的日期  yyyy-MM-dd
     */
    public static String getNextDayWithPattern(Date date, String pattern) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return new SimpleDateFormat(pattern).format(calendar.getTime());
    }

    /**
     * 时间戳转换String类型
     *
     * @param param 时间戳
     * @return date
     */
    public static String timeStampToString(String param) {

        SimpleDateFormat format = new SimpleDateFormat(DateUtil.settlePattern);
        String str = format.format(FormatUtil.toLong(param));
        return str;
    }
}

