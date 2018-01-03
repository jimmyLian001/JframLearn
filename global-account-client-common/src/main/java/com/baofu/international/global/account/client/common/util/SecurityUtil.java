package com.baofu.international.global.account.client.common.util;


import com.baofu.international.global.account.client.common.constant.CommonDict;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * 加密工具
 * <p>
 * 1.md5加密
 * 2.DES加密
 * </p>
 *
 * @author : 不良人
 * @version : 1.0.0
 * @date : 2017/11/5
 */
@Slf4j
public class SecurityUtil {

    private SecurityUtil() {
    }

    /**
     * MD5 加密
     *
     * @param str 要加密参数
     * @return 加密后值
     */
    public static String md5DesEncrypt(String str) {

        try {
            MessageDigest md5 = MessageDigest.getInstance(CommonDict.MD5);
            md5.update(str.getBytes(StandardCharsets.UTF_8));
            byte[] digest = md5.digest();
            StringBuilder hexString = new StringBuilder();
            String strTemp;
            for (int i = 0; i < digest.length; i++) {
                strTemp = Integer.toHexString((digest[i] & 0x000000FF) | 0xFFFFFF00).substring(6);
                hexString.append(strTemp);
            }
            return hexString.toString();
        } catch (Exception e) {
            log.error("call MD5加密异常", e);
        }
        return str;
    }

    /**
     * DES加密
     *
     * @param source 加密内容
     * @param desKey 加密秘钥
     * @return 加密后值
     * @throws Exception
     */
    public static String desEncrypt(String source, String desKey) throws NoSuchAlgorithmException, InvalidKeyException,
            InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException {
        // 从原始密匙数据创建DESKeySpec对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(CommonDict.DES);
        SecretKey secureKey = keyFactory.generateSecret(new DESKeySpec(desKey.getBytes(StandardCharsets.UTF_8)));
        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(CommonDict.DES);
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, secureKey);
        // 现在，获取数据并加密
        byte[] destBytes = cipher.doFinal(source.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexRetSB = new StringBuilder();
        for (byte b : destBytes) {
            String hexString = Integer.toHexString(0x00ff & b);
            hexRetSB.append(hexString.length() == 1 ? 0 : "").append(hexString);
        }
        return hexRetSB.toString();
    }

}
