package com.baofu.international.global.account.client.web.util;

import com.baofu.international.global.account.client.web.models.ValidateCodeInfo;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.SecureRandom;

/**
 * description：验证码
 * <p/>
 *
 * @author : liy on 2017/11/5
 * @version : 1.0.0
 */
@Slf4j
public class ValidateCode {

    private ValidateCode() {
    }

    /**
     * Random
     */
    private static SecureRandom random = new SecureRandom();

    /**
     * 生成验证码
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param codeInfo codeInfo
     */
    public static void getVerify(HttpServletRequest request, HttpServletResponse response, ValidateCodeInfo codeInfo) {
        //设置相应类型,告诉浏览器输出的内容为图片
        response.setContentType("image/jpeg");
        //设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        try {
            //输出验证码图片方法
            getRandcode(request, response, codeInfo);
        } catch (Exception e) {
            log.info("生成验证码异常", e);
        }
    }

    /**
     * 生成随机图片
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    private static void getRandcode(HttpServletRequest request, HttpServletResponse response, ValidateCodeInfo codeInfo) {
        HttpSession session = request.getSession();
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(codeInfo.getImgWidth(), codeInfo.getImgHeight(), BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics(); // 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, codeInfo.getImgWidth(), codeInfo.getImgHeight());
        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, codeInfo.getFontSize()));
        g.setColor(getRandColor(110, 133));
        // 绘制干扰线
        for (int i = 0; i <= codeInfo.getLineNum(); i++) {
            drawLine(g, codeInfo);
        }
        // 绘制随机字符
        String randomString = "";
        for (int i = 1; i <= codeInfo.getRandNum(); i++) {
            randomString = drawString(g, randomString, i, codeInfo.getRandStr());
        }
        //将生成的随机字符串保存到session中，而jsp界面通过session.getAttribute("RANDOMCODEKEY")，
        //获得生成的验证码，然后跟用户输入的进行比较
        log.info("生成的验证码：{},session:{}", randomString, codeInfo.getSession());
        session.removeAttribute(codeInfo.getSession());
        session.setAttribute(codeInfo.getSession(), randomString);

        g.dispose();
        try {
            // 将内存中的图片通过流动形式输出到客户端
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (Exception e) {
            log.info("生成随机图片异常", e);
        }

    }

    /**
     * 颜色
     *
     * @param fc fc
     * @param bc bc
     * @return
     */
    private static Color getRandColor(int fc, int bc) {
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc - 16);
        int g = fc + random.nextInt(bc - fc - 14);
        int b = fc + random.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }


    /**
     * 绘制字符串
     *
     * @param g            g
     * @param randomString randomString
     * @param i            i
     * @param randStr      randStr
     * @return 结果集
     */
    private static String drawString(Graphics g, String randomString, int i, String randStr) {
        g.setFont(new Font("Fixedsys", Font.CENTER_BASELINE, 25));
        g.setColor(new Color(random.nextInt(101), random.nextInt(111), random
                .nextInt(121)));
        String rand = String.valueOf(getRandomString(randStr));
        randomString += rand;
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(rand, 13 * i, 30);
        return randomString;
    }

    /**
     * 绘制干扰线
     *
     * @param g g
     */
    private static void drawLine(Graphics g, ValidateCodeInfo codeInfo) {
        int x = random.nextInt(codeInfo.getImgWidth());
        int y = random.nextInt(codeInfo.getImgHeight());
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }

    /**
     * 获取随机的字符
     *
     * @param randStr randStr
     * @return 验证码
     */
    public static String getRandomString(String randStr) {
        int num = random.nextInt(randStr.length());
        return String.valueOf(randStr.charAt(num));
    }

}
