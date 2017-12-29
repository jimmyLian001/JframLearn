package com.baofu.cbpayservice.biz;

import com.baofu.cbpayservice.biz.models.CreateOrderDetailBo;

import java.io.IOException;

/**
 * 创建汇款文件服务
 * <p>
 *     1、创建电商文件
 *     2、创建机票文件
 *     3、创建留学文件
 *     4、创建酒店文件
 *     5、创建旅游文件
 * </p>
 * User: 不良人 Date:2017/10/20 ProjectName: cbpayservice Version: 1.0
 */
public interface CreateRemitFileBiz {

    /**
     * 创建电商文件
     *
     * @param detailBo mq内容
     */
    void electronicCommerce(CreateOrderDetailBo detailBo);

    /**
     * 创建机票文件
     *
     * @param detailBo mq内容
     */
    void airTickets(CreateOrderDetailBo detailBo);

    /**
     * 创建留学文件
     *
     * @param detailBo mq内容
     */
    void studyAbroad(CreateOrderDetailBo detailBo);

    /**
     * 创建酒店文件
     *
     * @param detailBo mq内容
     */
    void hotel(CreateOrderDetailBo detailBo);

    /**
     * 创建旅游文件
     *
     * @param detailBo mq内容
     */
    void tourism(CreateOrderDetailBo detailBo);
}
