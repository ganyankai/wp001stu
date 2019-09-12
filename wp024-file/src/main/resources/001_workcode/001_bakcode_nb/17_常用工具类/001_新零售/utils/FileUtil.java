package com.zrytech.framework.newshop.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSException;
import com.zrytech.framework.base.constant.Constant;
import com.zrytech.framework.base.enums.BaseResult;
import com.zrytech.framework.base.enums.BaseResultEnum;
import com.zrytech.framework.base.enums.FileTypeEnum;
import com.zrytech.framework.base.exception.BusinessException;
import com.zrytech.framework.common.config.OssConfiguration;
import com.zrytech.framework.common.util.OssServerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Component
public class FileUtil {

    @Value("${file.tmp.dir}")
    private String tmpDir;

    private static OssConfiguration ossConfiguration;

    @Autowired
    public void setOssConfiguration(OssConfiguration ossConfiguration) {
        this.ossConfiguration = ossConfiguration;
    }


    /**
     * @Desinition:生成本地临时文件
     * @author:qufei
     * @return:ServerResponse
     */
    public   String createLocalTempFile(MultipartFile multipartFile){
        java.io.File file=new java.io.File(tmpDir);
        if(!file.exists()){
            file.mkdir();
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String tmpFileName=System.currentTimeMillis()+ FileUtil.getFileSuffixes(originalFilename);//生成零时文件名称
        String localfilePath=tmpDir+tmpFileName;
        java.io.File tmpfile = new java.io.File(localfilePath); //生成本地零时文件
        try {
            multipartFile.transferTo(tmpfile);
        } catch (IOException e) {
            log.error("生成本地零时文件出错：",e);
            throw new BusinessException(new BaseResult(BaseResultEnum.FILE_UPLOAD_ERROR));
        }
        return localfilePath;
    }

    /**
     * 根据文件全路径（或文件名）获取文件后缀  .img
     * @param fileName
     * @return
     */
    public static String getFileSuffixes(String fileName){
        int suffixIndex = fileName.lastIndexOf(".");
        if(suffixIndex == -1){
            throw new BusinessException(new BaseResult(BaseResultEnum.FILE_UPLOAD_ERROR));
        }
        return fileName.substring(suffixIndex);
    }

    /**
     * 根据文件全路径（或文件名）获取文件类型
     * @return
     */
    public static FileTypeEnum getFileType(String fileName){
        String fileSuffixes = getFileSuffixes(fileName);
        String fileSuffixesLowerCase = fileSuffixes.toLowerCase();
        if(Constant.picture.contains(fileSuffixesLowerCase)){
            return FileTypeEnum.picture;
        }
        if(Constant.video.contains(fileSuffixesLowerCase)){
            return FileTypeEnum.video;
        }
        if(Constant.text.contains(fileSuffixesLowerCase)){
            return FileTypeEnum.text;
        }
        return FileTypeEnum.other;
    }

    /**
     * @Desinition:上传视频到OSS
     * @author:qufei
     * @return:ServerResponse
     */
    public String uploadVideo(String imgUrl) {
        String fileType=imgUrl.substring(imgUrl.lastIndexOf("."));
        StringBuffer urlPath = new StringBuffer();
        urlPath.append("https://");
        urlPath.append(ossConfiguration.getBucket_name());
        urlPath.append(".");
        urlPath.append(ossConfiguration.getEnd_point());
        urlPath.append("/");
        CodeUtils codeUtils = new CodeUtils();
        String objectKey = "video/"+codeUtils.createSoNumber()+fileType;
        try {
            InputStream inputStream = new FileInputStream(imgUrl);
            OssServerUtils.uploadFileInputStream(objectKey,inputStream);
            urlPath.append(objectKey);
        }catch(OSSException | ClientException |IOException ex) {
            log.info("上传文件失败，请检查。"+ ex.getMessage());
        }
        log.info("path>>>>>>>>>>>>>"+urlPath.toString());
        return urlPath.toString();

    }

    /**
     * @Desinition:上传视频到OSS
     * @author:qufei
     * @return:ServerResponse
     */
    public String uploadVideo(InputStream inputStream) {
        String qrdir=".jpg";
        StringBuffer urlPath = new StringBuffer();
        urlPath.append("https://");
        urlPath.append(ossConfiguration.getBucket_name());
        urlPath.append(".");
        urlPath.append(ossConfiguration.getEnd_point());
        urlPath.append("/");
        CodeUtils codeUtils = new CodeUtils();
        String objectKey = "video/"+codeUtils.createSoNumber()+qrdir;
        try {
            OssServerUtils.uploadFileInputStream(objectKey,inputStream);
            urlPath.append(objectKey);
        }catch(Exception e) {
            log.info("上传文件失败，请检查。");
        }
        log.info("path>>>>>>>>>>>>>"+urlPath.toString());
        return urlPath.toString();

    }
}
