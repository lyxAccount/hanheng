package com.example.interfacedemo.controller.aliyun;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import com.example.interfacedemo.controller.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@RestController
@RequestMapping("/oss")
public class OssController {

    @Autowired
    private OssService ossService;

    @Value("${aliyun.oss.file.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.file.keyid}")
    private String accessKeyId;
    @Value("${aliyun.oss.file.keysecret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.file.bucketname}")
    private String bucketname;

    @PostMapping("/upload")
    public String uploadOss(@RequestParam("file") MultipartFile file){
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            //上传文件的输入流
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String filename = file.getOriginalFilename();

            //生成唯一的文件名
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            filename=uuid+filename;

            //按日期文件夹分类
            String datePath = "oss/test";
            filename=datePath+"/"+filename;

            // 创建PutObjectRequest对象。
            //第一个参数 Bucket名称
            //第二个参数 上传到oss文件路径和文件名称
            //第三个参数 上传文件输入流

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketname, filename, inputStream);

            // 如果需要上传时设置存储类型与访问权限，请参考以下示例代码。
            // ObjectMetadata metadata = new ObjectMetadata();
            // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);

            // 上传文件。
            ossClient.putObject(putObjectRequest);

            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传文件之后的路径返回
            String url="";
            url="http://" + bucketname + "." + endpoint + "/" + filename;
            return url;

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("/download")
    public String downloadFile(String fileName, HttpServletResponse response) throws Exception{

        // 文件名以附件的形式下载
        response.setCharacterEncoding("utf-8");
        response.setContentType("image/jpeg");
        //在实际开发中 这里的路径应该是从数据库查询出来的
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        String fileDir = "oss/test";
        String ossPath=fileDir+"/"+fileName;

        OSS oss = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        OSSObject ossObject = oss.getObject(bucketname, ossPath);
        //获取文件流
        InputStream in = ossObject.getObjectContent();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            BufferedOutputStream out = new BufferedOutputStream(outputStream);
            //定义一个缓冲数组
            byte[] bytes = new byte[1024];
            int len=0;
            //从输入流读取字节  放在缓存数组  赋值给len  依次循环
            while ((len=in.read(bytes))!=-1){
                out.write(bytes,0,len);
            }
            if (out != null) {
                out.flush();
                out.close();
            }
            if (in != null) {
                in.close();
            }
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
            return  "failed";
        }
    }
}
