package com.example.interfacedemo.controller.sys;

import com.example.interfacedemo.controller.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/send")
    public String sendMail(@RequestParam String to,@RequestParam String subject,@RequestParam String content) throws MessagingException {

        String result = mailService.sendSimple(to,subject,content);
        return result;
    }
}
