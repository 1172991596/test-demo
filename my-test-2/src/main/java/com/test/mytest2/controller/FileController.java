package com.test.mytest2.controller;


import cn.hutool.core.io.FileUtil;
import com.test.mytest2.common.BaseResponse;
import com.test.mytest2.common.ResultUtils;
import com.test.mytest2.model.FileUploadDTO;
import com.test.mytest2.utils.KodoUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

/**
 * 文件上传
 */
@RestController
@RequestMapping("/uploadImg")
@Slf4j
@CrossOrigin(originPatterns = "*",allowCredentials = "true",allowedHeaders = "*")
public class FileController {


    @Autowired
    private KodoUtils kodoUtils;


    private final String DOMAIN = "http://s3159m8av.hn-bkt.clouddn.com/";

    private final String STORESPACE = "user_file_store";
    /**
     * 文件上传（使用七牛sdk的断点续传）
     *
     * @param
     * @return
     */
    @PostMapping("")
    public BaseResponse<String> uploadImg(@RequestParam Long id,@RequestPart("img") MultipartFile multipartFile) {


        validAvatar(multipartFile, "user_avatar");
        // 文件目录：根据业务、用户来划分
        String uuid = RandomStringUtils.randomAlphanumeric(8);
        String filename = uuid + "-" + multipartFile.getOriginalFilename();
        String filepath = String.format(STORESPACE+"/%s/%s/%s","user_avatar",id,filename);
        File file = null;
        try {
            //转换水印
            SimpleDateFormat dateFormat = new SimpleDateFormat("", Locale.SIMPLIFIED_CHINESE);
            dateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
//		String date = dateFormat.format(System.currentTimeMillis());
            String date = dateFormat.format(new Date());
            System.out.println(date);
            MultipartFile multipartFile1 = addWorkMarkToMutipartFile(multipartFile, date);
            // 上传文件到七牛云
            file = File.createTempFile(filepath, null);
            multipartFile1.transferTo(file);
            //添加水印
            System.out.println(filepath);
            System.out.println(file);
            System.out.println(DOMAIN + filepath);
            kodoUtils.sliceUpload(file,filepath);
            // 返回可访问地址
            return ResultUtils.success(DOMAIN + filepath);
        } catch (Exception e) {
            log.error("file upload error, filepath = " + filepath, e);
            return ResultUtils.error();
        } finally {
            if (file != null) {
                // 删除临时文件
                boolean delete = file.delete();
                if (!delete) {
                    log.error("file delete error, filepath = {}", filepath);
                }
            }
        }
    }


    /**
     * 校验头像
     *
     * @param multipartFile
     * @param fileUploadType 业务类型
     */
    private void validAvatar(MultipartFile multipartFile, String fileUploadType) {
        // 文件大小
        long fileSize = multipartFile.getSize();
        // 文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        final long ONE_M = 1024 * 1024L;
        //如果等于
        if (fileSize > ONE_M) {
            log.error("文件大小过大");
            return;
        }
        if (!Arrays.asList("jpeg", "jpg", "svg", "png", "webp").contains(fileSuffix)) {
            log.error("文件类型错误");
            return;
        }
    }

    public static MultipartFile addWorkMarkToMutipartFile(MultipartFile multipartFile,
                                                          String word) throws IOException {
        // 获取图片文件名 xxx.png xxx
        String originFileName = multipartFile.getOriginalFilename();
        // 获取原图片后缀 png
        int lastSplit = originFileName.lastIndexOf(".");
        String suffix = originFileName.substring(lastSplit + 1);
        // 获取图片原始信息
        String dOriginFileName = multipartFile.getOriginalFilename();
        String dContentType = multipartFile.getContentType();
        // 是图片且不是gif才加水印
        if (!suffix.equalsIgnoreCase("gif") && dContentType.contains("image")) {
            // 获取水印图片
            InputStream inputImg = multipartFile.getInputStream();
            Image img = ImageIO.read(inputImg);
            // 加图片水印
            int imgWidth = img.getWidth(null);
            int imgHeight = img.getHeight(null);

            BufferedImage bufImg = new BufferedImage(imgWidth, imgHeight,
                    BufferedImage.TYPE_INT_RGB);
            //设置字体
            Font font = new Font("宋体", Font.BOLD, 30);
            //调用画文字水印的方法
            markWord(bufImg, img, word, font , Color.gray, 0, 14);
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            ImageOutputStream imOut = ImageIO.createImageOutputStream(bs);
            ImageIO.write(bufImg, suffix, imOut);
            InputStream is = new ByteArrayInputStream(bs.toByteArray());

            // 加水印后的文件上传
            multipartFile = new MockMultipartFile(dOriginFileName, dOriginFileName, dContentType,
                    is);
        }
        //返回加了水印的上传对象
        return multipartFile;
    }

    public static void markWord(BufferedImage bufImg, Image img, String text, Font font, Color color, int x, int y) {
        //取到画笔
        Graphics2D g = bufImg.createGraphics();
        //画底片
        g.drawImage(img, 0, 0, bufImg.getWidth(), bufImg.getHeight(), null);
        g.setColor(color);
        g.setFont(font);
        //位置
        g.drawString(text, x, y);
        g.dispose();
    }


}
