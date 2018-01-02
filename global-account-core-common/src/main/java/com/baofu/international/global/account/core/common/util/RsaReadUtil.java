package com.baofu.international.global.account.core.common.util;


import com.alibaba.fastjson.util.Base64;
import com.baofu.international.global.account.core.common.constant.RsaConst;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;

/**
 * <b>公私钥读取工具</b><br>
 * <br>
 *
 * @author 行者
 * @version 4.1.0
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RsaReadUtil {

    /**
     * 根据Cer文件读取公钥
     *
     * @param pubCerPath
     * @return
     */
    public static PublicKey getPublicKeyFromFile(String pubCerPath) {

        try (InputStream pubKeyStream = Files.newInputStream(Paths.get(pubCerPath));) {
            byte[] reads = new byte[pubKeyStream.available()];
            pubKeyStream.read(reads);
            return getPublicKeyByText(new String(reads, StandardCharsets.ISO_8859_1));
        } catch (FileNotFoundException e) {
            log.error("公钥文件不存在:", e);
        } catch (IOException e) {
            log.error("公钥文件读取失败:", e);
        }
        return null;
    }

    /**
     * 根据公钥Cer文本串读取公钥
     *
     * @param pubKeyText 公钥串
     * @return 返回公钥接口对象
     */
    public static PublicKey getPublicKeyByText(String pubKeyText) {

        try (BufferedReader br = new BufferedReader(new StringReader(pubKeyText));) {
            CertificateFactory certificateFactory = CertificateFactory.getInstance(RsaConst.KEY_X509);
            String line;
            StringBuilder keyBuffer = new StringBuilder();
            while ((line = br.readLine()) != null) {
                if (!line.startsWith("-")) {
                    keyBuffer.append(line);
                }
            }
            Certificate certificate = certificateFactory.generateCertificate(
                    new ByteArrayInputStream(Base64.decodeFast(keyBuffer.toString())));
            return certificate.getPublicKey();
        } catch (Exception e) {
            log.error("解析公钥内容失败:", e);
        }
        return null;
    }

    /**
     * 根据私钥路径读取私钥
     *
     * @param pfxPath    私钥文件路径
     * @param priKeyPass 私钥密码
     * @return 返回私钥接口对象
     */
    public static PrivateKey getPrivateKeyFromFile(String pfxPath, String priKeyPass) {

        try (InputStream priKeyStream = Files.newInputStream(Paths.get(pfxPath))) {

            byte[] reads = new byte[priKeyStream.available()];
            priKeyStream.read(reads);
            return getPrivateKeyByStream(reads, priKeyPass);
        } catch (Exception e) {
            log.error("解析文件，读取私钥失败:", e);
        }
        return null;
    }

    /**
     * 根据PFX私钥字节流读取私钥
     *
     * @param pfxBytes   私钥字节
     * @param priKeyPass 私钥密钥
     * @return 返回私钥接口对象
     */
    public static PrivateKey getPrivateKeyByStream(byte[] pfxBytes, String priKeyPass) {
        try {
            KeyStore ks = KeyStore.getInstance(RsaConst.KEY_PKCS12);
            char[] charPriKeyPass = priKeyPass.toCharArray();
            ks.load(new ByteArrayInputStream(pfxBytes), charPriKeyPass);
            Enumeration<String> aliasEnum = ks.aliases();
            String keyAlias = null;
            if (aliasEnum.hasMoreElements()) {
                keyAlias = aliasEnum.nextElement();
            }
            return (PrivateKey) ks.getKey(keyAlias, charPriKeyPass);
        } catch (IOException e) {
            // 加密失败
            log.error("解析文件，读取私钥失败:", e);
        } catch (KeyStoreException e) {
            log.error("私钥存储异常:", e);
        } catch (NoSuchAlgorithmException e) {
            log.error("不存在的解密算法:", e);
        } catch (CertificateException e) {
            log.error("证书异常:", e);
        } catch (UnrecoverableKeyException e) {
            log.error("不可恢复的秘钥异常", e);
        }
        return null;
    }
}
