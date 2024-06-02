package com.sagiri.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sagiri.entity.MailCode;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MailCodeMapper extends BaseMapper<MailCode> {

    int deleteByMail (String mail);
}
