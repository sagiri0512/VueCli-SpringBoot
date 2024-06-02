package com.sagiri.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class MailCode {
    @TableId
    private long mId;
    private String mailCode;
    private java.sql.Timestamp mailCodeTime;
    private String mail;
}
