package com.example.interfacedemo.controller.conroller;

import com.example.interfacedemo.util.ZipCompressor;
import com.sun.deploy.net.URLEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/file")
public class FileController {
    /**
     * 下载文件夹，（批量下载文件时可以先将文件复制到某个临时目录，再将这个目录压缩下载）
     */

    @PostMapping("/download/dir")
    private HttpServletResponse downDestroy( HttpServletResponse response){
        File file = new File("E:/target");
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


}
