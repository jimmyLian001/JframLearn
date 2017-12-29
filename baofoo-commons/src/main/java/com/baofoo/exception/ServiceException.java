package com.baofoo.exception;

/**
 * 服务异常，该异常为内部处理异常，不能暴露给最终用户，主要作用是将一些受检异常包装为非受检异常<br>
 * 在涉及业务数据，比如订单号，商户订单号，当前余额具体数值时，建议使用构造方法ServiceException(String,
 * String)或ServiceException(String, String,
 * Throwable)，将此类信息写入businessMessage，而message中做比较笼统的提示，比如操作发生某种类型的错误
 *
 * @author yuqih
 */
public class ServiceException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String businessMessage;

    /**
     * @param message
     */
    public ServiceException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param businessMessage 业务数据，比如订单号等信息
     */
    public ServiceException(String message, String businessMessage) {
        this(message);
        this.businessMessage = businessMessage;
    }

    /**
     * @param message
     * @param throwable
     */
    public ServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }

    /**
     * @param message
     * @param businessMessage 业务数据，比如订单号等信息
     * @param throwable
     */
    public ServiceException(String message, String businessMessage,
                            Throwable throwable) {
        this(message, throwable);
        this.businessMessage = businessMessage;
    }

    /**
     * @return the businessMessage
     */
    public String getBusinessMessage() {
        return businessMessage;
    }
}
