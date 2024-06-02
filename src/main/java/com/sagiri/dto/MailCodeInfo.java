package com.sagiri.dto;

import lombok.Data;

@Data
public class MailCodeInfo {//用于从用户使用邮箱登录时获取邮箱和验证码
    String mail;
    String code;
}
