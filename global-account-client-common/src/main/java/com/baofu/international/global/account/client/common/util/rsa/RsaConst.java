package com.baofu.international.global.account.client.common.util.rsa;

/**
 * Rsa常数
 */
public final class RsaConst {
    /**
     * 编码
     */
    public static final String ENCODE = "UTF-8";
    /**
     * KEY_X509
     */
    public static final String KEY_X509 = "X509";
    /**
     * KEY_PKCS12
     */
    public static final String KEY_PKCS12 = "PKCS12";
    /**
     * KEY_ALGORITHM
     */
    public static final String KEY_ALGORITHM = "RSA";
    /**
     * CER_ALGORITHM
     */
    public static final String CER_ALGORITHM = "MD5WithRSA";
    /**
     * RSA_CHIPER
     */
    public static final String RSA_CHIPER = "RSA/ECB/PKCS1Padding";
    /**
     * KEY_SIZE
     */
    public static final int KEY_SIZE = 1024;
    /**
     * 1024bit 加密块 大小
     */
    public static final int ENCRYPT_KEYSIZE = 117;
    /**
     * 1024bit 解密块 大小
     */
    public static final int DECRYPT_KEYSIZE = 128;
    private RsaConst() {
    }
}
