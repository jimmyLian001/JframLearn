package com.baofu.international.global.account.client.common.util.rsa;


import com.alibaba.fastjson.util.Base64;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

/**
 * <b>公私钥读取工具</b><br>
 * <br>
 *
 * @author 行者
 * @version 4.1.0
 */
@Slf4j
@SuppressWarnings("restriction")
public final class RsaReadUtil {
    private RsaReadUtil(){

    }
    /**
     * 根据Cer文件读取公钥
     *
     * @param pubCerPath Cer文件
     * @return PublicKey
     */
    public static PublicKey getPublicKeyFromFile(String pubCerPath) {
        FileInputStream pubKeyStream = null;
        try {
            pubKeyStream = new FileInputStream(pubCerPath);
            byte[] reads = new byte[pubKeyStream.available()];
            pubKeyStream.read(reads);
            return getPublicKeyByText(new String(reads, StandardCharsets.ISO_8859_1));
        } catch (FileNotFoundException e) {
            log.error("公钥文件不存在:", e);
        } catch (IOException e) {
            log.error("公钥文件读取失败:", e);
        } finally {
            IOUtils.closeQuietly(pubKeyStream);
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
        BufferedReader br = null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance(RsaConst.KEY_X509);
            br = new BufferedReader(new StringReader(pubKeyText));
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
        } finally {
            IOUtils.closeQuietly(br);
        }
        return null;
    }
}
