package com.sagiri.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@RestController
public class HelloController {
    @Autowired
    private JavaMailSender Sender;
    private String code = "";//验证码
    private String subject = "验证码";//邮箱标题
    private String contentType = "，在5分钟内有效，如非本人操作，请忽略本条邮件！";//邮箱内容--在验证码之后的提示
    private String from = "sagiri0512@qq.com";//邮箱发送者
    @PostMapping(value = "/mailTo") //获取验证码

    public String mailTo(@RequestBody String userMail) throws UnsupportedEncodingException {//邮箱接收者为userMail
        String decodedMail = URLDecoder.decode(userMail, "UTF-8");//用户邮箱转化成UTF-8格式
        if (decodedMail.endsWith("=")) {// 去除末尾的等号
            decodedMail = decodedMail.substring(0, decodedMail.length() - 1);
        }
        String content = "你的验证码为 : ";
        code = (int)((Math.random()*9+1)*100000)+"";//生成6位验证码
        content = content + code + contentType;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject(subject);
        message.setText(content);
        message.setTo(decodedMail);
        message.setFrom(from);
        try{
            Sender.send(message);
            return "1";
        } catch (MailException e) {
            return "0";
        }
    }
    @PostMapping("/mailLogin")//使用邮箱登录时判断邮箱验证码是否正确
    public String mailLogin(@RequestBody String mailCode){
        if (mailCode.endsWith("=")) {
            mailCode = mailCode.substring(0, mailCode.length() - 1);
        }
        if (code.equals(mailCode)){
            return "1";
        }else{
            return "0";
        }
    }
}
