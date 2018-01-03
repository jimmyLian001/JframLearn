package com.baofu.international.global.account.client.common.util.rsa;


import com.baofu.international.global.account.client.common.constant.NumberDict;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;


/**
 * <b>Rsa加解密工具</b><br>
 * <br>
 * 公钥采用X509,Cer格式的<br>
 * 私钥采用PKCS12加密方式的PFX私钥文件<br>
 * 加密算法为1024位的RSA，填充算法为PKCS1Padding<br>
 *
 * @author 行者
 * @version 4.1.0
 */
@Slf4j
public final class RsaCodingUtil {
    private RsaCodingUtil() {
    }
    /**
     * 指定Cer公钥路径解密
     *
     * @param src        参数
     * @param pubCerPath 参数
     * @return decryptByPublicKey
     */
    public static String decryptByPubCerFile(String src, String pubCerPath) {
        PublicKey publicKey = RsaReadUtil.getPublicKeyFromFile(pubCerPath);
        if (publicKey == null) {
            return "";
        }
        return decryptByPublicKey(src, publicKey);
    }


    /**
     * 根据公钥解密
     *
     * @param src       参数
     * @param publicKey 参数
     * @return String
     */
    public static String decryptByPublicKey(String src, PublicKey publicKey) {

        byte[] destBytes = rsaByPublicKey(hex2Bytes(src), publicKey, Cipher.DECRYPT_MODE);

        if (destBytes == null) {
            return "";
        }
        return new String(destBytes, StandardCharsets.UTF_8);
    }

    // ======================================================================================
    // 公私钥算法
    // ======================================================================================

    /**
     * 公钥算法
     *
     * @param srcData   源字节
     * @param publicKey 公钥
     * @param mode      加密 OR 解密
     * @return encryptedData
     */
    public static byte[] rsaByPublicKey(byte[] srcData, PublicKey publicKey, int mode) {
        try {
            Cipher cipher = Cipher.getInstance(RsaConst.RSA_CHIPER);
            cipher.init(mode, publicKey);
            // 分段加密
            int blockSize = (mode == Cipher.ENCRYPT_MODE) ? RsaConst.ENCRYPT_KEYSIZE : RsaConst.DECRYPT_KEYSIZE;
            byte[] encryptedData = null;
            for (int i = 0; i < srcData.length; i += blockSize) {
                // 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
                byte[] doFinal = cipher.doFinal(subarray(srcData, i, i + blockSize));
                encryptedData = addAll(encryptedData, doFinal);
            }
            return encryptedData;

        } catch (NoSuchAlgorithmException e) {
            log.error("公钥算法-不存在的解密算法:", e);
        } catch (NoSuchPaddingException e) {
            log.error("公钥算法-无效的补位算法:", e);
        } catch (IllegalBlockSizeException e) {
            log.error("公钥算法-无效的块大小:", e);
        } catch (BadPaddingException e) {
            log.error("公钥算法-补位算法异常:", e);
        } catch (InvalidKeyException e) {
            log.error("公钥算法-无效的私钥:", e);
        }
        return new byte[0];
    }

    /**
     * @param array               参数
     * @param startIndexInclusive 参数
     * @param endIndexExclusive   参数
     * @return subarray
     */
    public static byte[] subarray(byte[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return new byte[0];
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return new byte[0];
        }

        byte[] subarray = new byte[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * @param array1 参数
     * @param array2 参数
     * @return joinedArray
     */
    public static byte[] addAll(byte[] array1, byte[] array2) {
        if (array1 == null) {
            return clone(array2);
        } else if (array2 == null) {
            return clone(array1);
        }
        byte[] joinedArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    /**
     * @param array 参数
     * @return byteClone
     */
    public static byte[] clone(byte[] array) {
        if (array == null) {
            return new byte[0];
        }
        return (byte[]) array.clone();
    }

    /**
     * 将16进制字符串转为转换成字符串
     *
     * @param source 参数
     * @return String
     */
    public static byte[] hex2Bytes(String source) {
        byte[] sourceBytes = new byte[source.length() / NumberDict.TWO];
        for (int i = 0; i < sourceBytes.length; i++) {
            sourceBytes[i] = (byte) Integer.parseInt(source.substring(i * NumberDict.TWO, i * NumberDict.TWO + NumberDict.TWO),
                    NumberDict.SIXTEEN);
        }
        return sourceBytes;
    }
}
