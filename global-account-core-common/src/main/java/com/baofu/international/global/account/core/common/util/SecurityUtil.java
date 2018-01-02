package com.baofu.international.global.account.core.common.util;


import com.system.commons.exception.BizServiceException;
import com.system.commons.exception.CommonErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @author ximenchuixue
 * @since 2015/10/21
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtil {

    /**
     * DES加密
     */
    public static String desEncrypt(String source, String desKey) {
        try {
            // 从原始密匙数据创建DESKeySpec对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            // Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, keyFactory.generateSecret(new DESKeySpec(desKey.getBytes(StandardCharsets.UTF_8))));
            // 现在，获取数据并加密
            byte[] destBytes = cipher.doFinal(source.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexRetSB = new StringBuilder();
            for (byte b : destBytes) {
                String hexString = Integer.toHexString(0x00ff & b);
                hexRetSB.append(hexString.length() == 1 ? 0 : "").append(hexString);
            }
            return hexRetSB.toString();
        } catch (Exception e) {
            log.error("DES加密发生错误：", e);
            throw new BizServiceException(CommonErrorCode.UNEXPECTED_ERROR, "DES加密发生错误");
        }
    }

    /**
     * DES解密
     */
    public static String desDecrypt(String source, String desKey) {
        // 解密数据
        byte[] sourceBytes = new byte[source.length() / 2];
        for (int i = 0; i < sourceBytes.length; i++) {
            sourceBytes[i] = (byte) Integer.parseInt(source.substring(i * 2, i * 2 + 2), 16);
        }
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            Cipher cipher = Cipher.getInstance("DES");
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, keyFactory.generateSecret(new DESKeySpec(desKey.getBytes(StandardCharsets.UTF_8))));
            // 现在，获取数据并解密
            byte[] destBytes = cipher.doFinal(sourceBytes);
            return new String(destBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("DES解密发生错误：", e);
            throw new BizServiceException(CommonErrorCode.UNEXPECTED_ERROR, "DES解密发生错误");
        }
    }

}
