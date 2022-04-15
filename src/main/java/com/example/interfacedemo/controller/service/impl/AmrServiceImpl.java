package com.example.interfacedemo.controller.service.impl;

import com.example.interfacedemo.controller.service.AmrService;
import org.springframework.stereotype.Service;
import ws.schild.jave.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Service("amrService")
public class AmrServiceImpl implements AmrService {
    @Override
    public void amr() {
        File source = new File("E://test/record_1646378597181.amr");   //源文件
        File target = new File("E://test/record_1646378597181.mp3");   //目标文件
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder();
        try {

            MultimediaObject multimediaObject  = new MultimediaObject(source);
            encoder.encode(multimediaObject,target, attrs);
        } catch (IllegalArgumentException | EncoderException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testIo(HttpServletResponse response) throws Exception{
        String path1 = "E:\\test" + File.separator+"record_1646378597181.amr";
        String path2 = "E:\\test" + File.separator+"record_1646378597181.mp3";
        List<String> list = new ArrayList<>();
        list.add(path1);
        list.add(path2);
        ServletOutputStream outputStream = null;
        FileInputStream ips = null;
        for (int i = 0; i < list.size(); i++) {
            outputStream = null;
            ips = null;
            ips = new FileInputStream(new File(list.get(i)));
            response.setCharacterEncoding("utf-8");
            response.setContentType("audio/mp3");
            outputStream = response.getOutputStream();
            //读取文件流
            int len = 0;
            byte[] buffer = new byte[1024 * 10];
            while ((len = ips.read(buffer)) != -1){
                outputStream.write(buffer,0,len);
            }

        }
        outputStream.flush();
        outputStream.close();
        ips.close();
    }
}
