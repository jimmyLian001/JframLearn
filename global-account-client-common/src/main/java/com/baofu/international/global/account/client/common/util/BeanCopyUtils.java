package com.baofu.international.global.account.client.common.util;

import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * 对象转换
 * 底层使用Spring的转换方式
 * <p>
 * 1、对象转换工具类
 * 2、对象转换工具类重载
 * </p>
 * ProjectName:account-client
 *
 * @author 香克斯
 * @version : 1.0
 * @date : 2017/12/5
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanCopyUtils {

    /**
     * 对象转换工具类
     *
     * @param source 源对象
     * @param tClass 需要转换之后的对象
     * @param <T>    实体
     * @return 返回对象
     */
    public static <T> T objectConvert(Object source, Class<T> tClass, String[] strings) {
        try {
            T t = tClass.newInstance();

            BeanUtils.copyProperties(source, t, strings);

            return t;
        } catch (Exception e) {
            log.error("对象转换工具类发生转换异常：", e);
            throw new BizServiceException(CommonErrorCode.UNEXPECTED_ERROR, "类对象转换异常");
        }
    }

    /**
     * 对象转换工具类
     *
     * @param source 源对象
     * @param tClass 需要转换之后的对象
     * @param <T>    实体
     * @return 返回对象
     */
    public static <T> T objectConvert(Object source, Class<T> tClass) {

        return objectConvert(source, tClass, null);
    }
}
