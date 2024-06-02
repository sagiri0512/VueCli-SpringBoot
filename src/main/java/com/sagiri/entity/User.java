package com.sagiri.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User {
  @TableId
  private long uid;
  private String uMail;
  private String uPwd;
  private String uName;
  private String uProfile;
}
