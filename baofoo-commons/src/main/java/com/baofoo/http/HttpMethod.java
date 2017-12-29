package com.baofoo.http;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

/**
 * @author yuqih
 */
@XmlType(name = "httpMethod")
@XmlEnum
public enum HttpMethod {
    GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS;

    /**
     * @param v
     * @return
     */
    public static HttpMethod fromValue(String v) {
        return valueOf(v);
    }

    /**
     * @return
     */
    public static HttpMethod getDefault() {
        return POST;
    }

    /**
     * @return
     */
    public String value() {
        return name();
    }
}
