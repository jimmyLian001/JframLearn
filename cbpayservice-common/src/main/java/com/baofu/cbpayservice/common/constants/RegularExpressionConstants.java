package com.baofu.cbpayservice.common.constants;

/**
 * 正则表达式常量
 * <p>
 * User: 不良人 Date:2017/6/23 ProjectName: cbpayservice Version: 1.0
 */
public class RegularExpressionConstants {

    /**
     * 时间格式：YYYY-MM-DD HH：MM
     */
    public static final String YYYYY_MM_DD_HH_MM = "^[1-2][0-9][0-9][0-9]-([1][0-2]|0?[1-9])-([12][0-9]|3[01]|0?[1-9]) ([01][0-9]|[2][0-3]):[0-5][0-9]$";
    /**
     * 时间格式：YYYYMMDDHHMM
     */
    public static final String YYYYYMMDDHHMM = "^[1-2][0-9][0-9][0-9]([1][0-2]|0?[1-9])([12][0-9]|3[01]|0?[1-9])([01][0-9]|[2][0-3])[0-5][0-9]$";
//    /**
//     * 时间格式：YYYYMMDD HH：MM
//     */
//    public static final String YYYYYMMDD_HH_MM = "^[1-2][0-9][0-9][0-9]([1][0-2]|0?[1-9])([12][0-9]|3[01]|0?[1-9]) ([01][0-9]|[2][0-3]):[0-5][0-9]$";
//    /**
//     * 时间格式：YYYY/MM/DD HH：MM
//     */
//    public static final String _YYYYYMMDD_HH_MM = "^[1-2][0-9][0-9][0-9]/([1][0-2]|0?[1-9])/([12][0-9]|3[01]|0?[1-9]) ([01][0-9]|[2][0-3]):[0-5][0-9]$";
    /**
     * 日期格式：YYYY-MM-DD
     */
    public static final String YYYY_MM_DD = "^[1-2][0-9][0-9][0-9]-([1][0-2]|0?[1-9])-([12][0-9]|3[01]|0?[1-9])$";
    /**
     * 日期格式：YYYYMMDD
     */
    public static final String YYYYMMDD = "^[1-2][0-9][0-9][0-9]([1][0-2]|0?[1-9])([12][0-9]|3[01]|0?[1-9])$";
//    /**
//     * 日期格式：YYYY/MM/DD
//     */
//    public static final String YYYYMMDD = "^[1-2][0-9][0-9][0-9]/([1][0-2]|0?[1-9])/([12][0-9]|3[01]|0?[1-9])$";
}
