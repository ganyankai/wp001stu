package com.zrytech.framework.newshop.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.zrytech.framework.newshop.contants.NewShopConstant;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


/*
<!-- https://mvnrepository.com/artifact/com.google.zxing/core -->
<dependency>
    <groupId>com.google.zxing</groupId>
    <artifactId>core</artifactId>
    <version>3.3.3</version>
</dependency>
*/
@Component
public class QRCodeUtils2 {

    private static final int BLACK = 0xFF000000;

    private static final int WHITE = 0xFFFFFFFF;

    private static final Map<EncodeHintType, Object> HINTS = new HashMap<>();

    static {
        HINTS.put(EncodeHintType.QR_VERSION, 7);
        HINTS.put(EncodeHintType.CHARACTER_SET, "utf-8");
        HINTS.put(EncodeHintType.MARGIN, 0);
        HINTS.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
    }

    /**
     * 文件夹路径，用来缓存图片的
     */
    //private static final String FILE_PATH = "/home/admin/other/qrImg";
    private static String FILE_PATH() {
        int lastIndex = NewShopConstant.FILE_PATH_URL.length() - 1;
        String lastChar = NewShopConstant.FILE_PATH_URL.substring(lastIndex);
        if (lastChar.equals("/")) {
            return NewShopConstant.FILE_PATH_URL.substring(0, lastIndex);
        }
        return NewShopConstant.FILE_PATH_URL;
    }

    /**
     * 260*260 logo图片
     **/
    private static InputStream getLogoImg() throws IOException {
        Resource resource = new ClassPathResource("imgs/b.png");
        InputStream inputStream = resource.getInputStream();
        return inputStream;
    }

    /**
     * 1260 * 1260  白色背景图位置
     **/
    private static InputStream getBackgroundImg() throws IOException {
        Resource resource = new ClassPathResource("imgs/qr1.png");
        InputStream inputStream = resource.getInputStream();
        return inputStream;
    }


    private static String generateQRCode(String text, int width, int height, String dir, String name) {
        try {
            String suffix = "jpg";
            BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, HINTS);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
                }
            }

            String tmpDir = String.format("%s/%s/tmp", FILE_PATH(), dir);
            File tmpDirFile = new File(tmpDir);
            if (!tmpDirFile.exists() || !tmpDirFile.isDirectory()) {
                System.out.println("//不存在");
                tmpDirFile.mkdirs();
            } else {
                System.out.println("//目录存在");
            }

            String tmpFilePath = String.format("%s/%s.%s", tmpDir, name, suffix);
            File tmp = new File(tmpFilePath);


            Thumbnails
                    .of(bufferedImage)
                    .scale(1f)
                    .outputQuality(1f)
                    .watermark(Positions.CENTER, ImageIO.read(getLogoImg()), 1f)
                    .toFile(tmp);

            Position position = new Position() {
                @Override
                public Point calculate(int enclosingWidth, int enclosingHeight,
                                       int width, int height, int insetLeft,
                                       int insetRight, int insetTop, int insetBottom) {
                    int x = (enclosingWidth / 2) - (width / 2);
                    return new Point(x, x);
                }
            };

            String retrurnDir = String.format("%s/%s", FILE_PATH(), dir);
            File dirInfo = new File(retrurnDir);
            if (!dirInfo.isDirectory()) {
                dirInfo.mkdir();
            }

            String returnFilePath = String.format("%s/%s.%s", retrurnDir, name, suffix);
            File fileInfo = new File(returnFilePath);

            Thumbnails
                    .of(ImageIO.read(getBackgroundImg()))
                    .scale(1f)
                    .outputQuality(1f)
                    .watermark(position, ImageIO.read(tmp), 1f)
//                    .watermark(Positions.BOTTOM_CENTER, createImage(name, new Font("Arial", Font.PLAIN, 140)), 1f)
                    .toFile(fileInfo);


            BufferedImage image = ImageIO.read(new File(returnFilePath));
            //要想保存这个对象的话你要把image声明为BufferedImage 类型
            ImageIO.write(image, suffix, new File(tmpFilePath));
            tmp.delete();
            File myFilePath = new File(tmpDir);
            myFilePath.delete(); // 删除空文件夹  
            return returnFilePath;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final Integer WIDTH = 910;

    private static final Integer HEIGHT = 180;

    private static BufferedImage createImage(String str, Font font) throws Exception {
        BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.setClip(0, 0, WIDTH, HEIGHT);
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);
        graphics.setColor(Color.black);
        graphics.setFont(font);
        for (int i = 0; i < 6; i++) {
            graphics.drawString(str, 0, 128);
        }
        graphics.dispose();
        return bufferedImage;
    }


    /**
     * 生成二维码
     *
     * @param text    要生成二维码的内容
     * @param content 二维码下方的数字编号
     */
    public static String generateQRcode(String text, String content, String name) {
        /*SimpleDateFormat sim = new SimpleDateFormat("yyMMdd");
        name=name.replace(sim.format(new Date()),sim.format(new Date())+"_");*/
        String str = generateQRCode(text, 1180, 1180, content, name);

        return str;
    }


    public static void main(String[] args) {
        String str = generateQRcode("https://www.midebest.com/app/index.html#/videoinfo", "1234567893", "");

        System.out.println(str);
    }


}
