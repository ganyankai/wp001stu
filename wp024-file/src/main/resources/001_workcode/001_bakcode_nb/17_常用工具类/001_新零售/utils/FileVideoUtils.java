package com.zrytech.framework.newshop.utils;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;

import javax.imageio.ImageIO;

import com.zrytech.framework.app.enums.CommonResult;
import com.zrytech.framework.app.enums.ResultEnum;
import com.zrytech.framework.base.constant.Constant;
import com.zrytech.framework.base.exception.BusinessException;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.MultimediaInfo;

public class FileVideoUtils {

    /**
     * 获取指定视频的帧并保存为图片至指定目录
     *
     * @param videofile 源视频文件路径
     * @param framefile 截取帧的图片存放路径
     * @throws Exception
     */
    public static void fetchFrame(String videofile, String framefile)
            throws Exception {
        File targetFile = new File(framefile);
        FFmpegFrameGrabber ff = new FFmpegFrameGrabber(videofile);
        ff.start();
        int lenght = ff.getLengthInFrames();
        int i = 0;
        Frame f = null;
        while (i < lenght) {
            // 过滤前5帧，避免出现全黑的图片，依自己情况而定
            f = ff.grabFrame();
            if ((i > 48) && (f.image != null)) {
                break;
            }
            i++;
        }
        //  IplImage img = f.image;
        int owidth = f.imageWidth;
        int oheight = f.imageHeight;
        // 对截取的帧进行等比例缩放
        int width = 300;
        int height = (int) (((double) width / owidth) * oheight);
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage fecthedImage = converter.getBufferedImage(f);
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        bi.getGraphics().drawImage(fecthedImage.getScaledInstance(width, height, Image.SCALE_SMOOTH),
                0, 0, null);
        ImageIO.write(bi, "jpg", targetFile);
        ff.stop();
    }

    /**
     *      * 
     *      * @描述：获取视频时长 
     *      * @时间：2018年8月28日 上午10:18:59
     *      * @param source
     *      * @return
     *     
     */
    public static String ReadVideoTime(String FileUrl) {
        File source = new File(FileUrl);
        if (source == null) {
            throw new BusinessException(120, "视频不存在");
        }
        String length = "";
        try {
            MultimediaObject instance = new MultimediaObject(source);
            MultimediaInfo result = instance.getInfo();
            long ls = result.getDuration() / 1000;
          /*  int hour = (int) (ls / 3600);
            int minute = (int) (ls % 3600) / 60;*/
            //int second = (int) (ls - hour * 3600 - minute * 60);
            int second = (int) ls;
            // length = hour + "时" + minute + "分" + second + "秒";
            length = second + "秒";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }


    /**
     *      * 
     *      * @描述：获取视频大小 
     *      * @时间：2018年8月28日 上午10:30:17
     *      * @param source
     *      * @return
     *     
     */
    public static String ReadVideoSize(String FileUrl) {
        File source = new File(FileUrl);
        FileChannel fc = null;
        String size = "";
        try {
            FileInputStream fis = new FileInputStream(source);
            fc = fis.getChannel();
            BigDecimal fileSize = new BigDecimal(fc.size());
            size = fileSize.divide(new BigDecimal(1048576), 2, RoundingMode.HALF_UP) + "MB";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != fc) {
                try {
                    fc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return size;
    }


    public static void main(String[] args) {
        String videofile = "E:/video/1557180180442127836.mp4";

        String framefile = "E:/video/video.jpg";
        System.out.println(Constant.FILE_URL_PREFIX + File.separator + "/");
        try {
            //  fetchFrame(videofile, framefile);
            // System.out.println(ReadVideoTime(videofile));
            System.out.println(ReadVideoSize(videofile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
