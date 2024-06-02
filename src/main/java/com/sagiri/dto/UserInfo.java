package com.sagiri.dto;

import lombok.Data;

//用于从前段获取注册信息的封装类
@Data
public class UserInfo {
    private String userMail;
    private String mailCode;
    private String userPwd;
}
