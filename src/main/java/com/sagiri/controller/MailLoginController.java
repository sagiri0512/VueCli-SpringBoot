package com.sagiri.controller;

import com.sagiri.dto.MailCodeInfo;
import com.sagiri.service.MailCodeService;
import com.sagiri.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@RestController
public class MailLoginController {
    @Autowired
    private JavaMailSender Sender;
    @Autowired
    private MailCodeService mailCodeService;
    @Autowired
    private UserService userService;
    private String subject = "登陆验证码";//邮箱标题
    private String contentType = "，在5分钟内有效，如非本人操作，请忽略本条邮件！";//邮箱内容--在验证码之后的提示
    private String from = "sagiri0512@qq.com";//邮箱发送者
    private String code = "";//存储验证码
    @PostMapping(value = "/mailTo") //获取验证码
    public String mailTo(@RequestBody String userMail) throws UnsupportedEncodingException {
        String decodedMail = URLDecoder.decode(userMail, "UTF-8");//用户邮箱转化成UTF-8格式
        if (decodedMail.endsWith("=")) {// 去除末尾的等号
            decodedMail = decodedMail.substring(0, decodedMail.length() - 1);
        }
        if(userService.getUserByColumn("uMail", decodedMail) == null){
            return "-1";//不存在该用户
        }
        if (!mailCodeService.isExceeding120Seconds(decodedMail)) {
            return "-2";//120s内发送过验证码
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
            mailCodeService.insertMailCode(code, decodedMail);
            return "1";
        } catch (MailException e) {
            return "0";
        }
    }
    @PostMapping("/mailLogin")
    public String mailLogin(@RequestBody MailCodeInfo mailCodeInfo){
        if(userService.getUserByColumn("uMail", mailCodeInfo.getMail()) == null){
            return "-1";//不存在该用户
        }
        if(mailCodeService.getMailCodeByColumn("mail", mailCodeInfo.getMail()) == null){//没有获取过验证码
            return "0";
        }
        if(mailCodeService.getMailCodeByColumn("mail", mailCodeInfo.getMail()).getMailCode().equals(mailCodeInfo.getCode())){//判断验证码是否相同
            mailCodeService.deleteByMail(mailCodeInfo.getMail());//使用后删除
            return "1";//判断验证码是否一致
        }
        return "0";//验证码不同
    }
}
