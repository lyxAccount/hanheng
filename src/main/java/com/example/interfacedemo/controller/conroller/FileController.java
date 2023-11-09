package com.example.interfacedemo.controller.conroller;

import com.example.interfacedemo.util.ZipCompressor;
import com.example.interfacedemo.util.fileUtil.FileUtil;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@RestController
@RequestMapping("/file")
public class FileController {
    /**
     * 下载文件夹，（批量下载文件时可以先将文件复制到某个临时目录，再将这个目录压缩下载）
     */

    @PostMapping("/download/dir")
    private HttpServletResponse downDestroy( HttpServletResponse response){
        //要压缩的目录
        File file = new File("E:/target");
        //放压缩包的目录，跟下面zipFilePath一致
        File dir = new File("E:/test");
        if(!dir.exists()){
            dir.mkdirs();
        }
        String zipFilePath = "E:/test/a"+"_"+System.currentTimeMillis()/1000+".zip";
        ZipCompressor zipCompressor = new ZipCompressor(zipFilePath);
        try {
            zipCompressor.compress(file.getPath());
            File zipFile = new File(zipFilePath);
            downFile(zipFile,response);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    private HttpServletResponse downFile(File file, HttpServletResponse response) {
        InputStream fis = null;
        OutputStream toClient = null;
        try {
            // 以流的形式下载文件。
            fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            // 清空response
            response.reset();
            toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            //如果输出的是中文名的文件，在此处就要用URLEncoder.encode方法进行处理
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            toClient.write(buffer);
            toClient.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally{
            try {
                File f = new File(file.getPath());
                f.delete();
                if(fis!=null){
                    fis.close();
                }
                if(toClient!=null){
                    toClient.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return response;
    }

    /**
     * 截取视频帧数
     */
    @PostMapping("/intercept/video")
    public String interceptVideo(HttpServletResponse response) throws Exception {

        String path = "D:/video/3cb19f6e8b56d4d8bca49c2fc55cbbc3.mp4";
        File file = new File(path);
        try {
            String imgFileString = FileUtil.fetchPic(file);
            //图片保存目录
            String tempPath = "D:/video/";
            //使用毫秒数作为图片名
            String fileName = System.currentTimeMillis() + ".jpg";
            File tempFile = new File(tempPath + fileName);
            if (imgFileString.indexOf("base64,") >= 0) {
                imgFileString = imgFileString.split("base64,")[1];
            }
            byte[] b = Base64.decodeBase64(imgFileString);
            for (int i = 0; i < b.length; ++i) {
                // 调整异常数据
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(tempFile);
            out.write(b);
            out.flush();
            out.close();

            ServletOutputStream outputStream = null;
            FileInputStream ips = null;
            ips = new FileInputStream(new File(tempFile.getAbsolutePath()));
            response.setCharacterEncoding("utf-8");
            response.setContentType("image/jpeg");
            outputStream = response.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1){
                outputStream.write(buffer,0,len);
            }
            outputStream.flush();
            outputStream.close();
            ips.close();

            return tempFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

}
