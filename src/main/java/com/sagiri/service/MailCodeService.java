package com.sagiri.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sagiri.mapper.MailCodeMapper;
import com.sagiri.entity.MailCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class MailCodeService {
    @Autowired
    private MailCodeMapper mailCodeMapper;
    @Autowired
    private ThreadPoolTaskScheduler taskScheduler;
    private MailCode mailCode = new MailCode();
    public void insertMailCode(String code, String userMail) {
        // 设置 MailCode 对象的属性
        mailCode.setMailCode(code);
        mailCode.setMail(userMail);
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        mailCode.setMailCodeTime(timestamp);
        // 调用 Mapper 的 insert 方法插入数据
        mailCodeMapper.insert(mailCode);
        // 调度删除任务在5分钟后执行
        scheduleDeletion(mailCode, 5 * 60 * 1000); // 5分钟后删除
    }

    @Async//异步操作
    public void scheduleDeletion(MailCode mailCodeToDelete, long delay) {
        Date scheduledTime = new Date(mailCodeToDelete.getMailCodeTime().getTime() + delay);
        taskScheduler.schedule(() -> deleteByMail(mailCodeToDelete.getMail()), scheduledTime);//延时，在指定时间执行值方法，后项为时间
        System.out.println(taskScheduler);
    }
    public MailCode getMailCodeByColumn(String columnName, String columnValue) {//根据列名的值查找一行
        QueryWrapper<MailCode> queryWrapper = Wrappers.query();
        queryWrapper.eq(columnName, columnValue);
        return mailCodeMapper.selectOne(queryWrapper);
    }
    public void deleteByMail(String mail){//删除验证码
        mailCodeMapper.deleteByMail(mail);
    }
    public boolean isExceeding120Seconds(String mail){//判断120s内是否发送过验证码
        MailCode mailCode = getMailCodeByColumn("Mail", mail);
        if(mailCode == null){
            return true;
        }
        // 获取验证码发送的时间并计算120秒后的时间
        Date mailCodeTime = mailCode.getMailCodeTime();
        Date scheduledTime = new Date(mailCodeTime.getTime() + 120 * 1000);
        // 获取当前时间
        Date currentTime = new Date();
        // 返回当前时间是否超过了120秒后的时间
        return currentTime.after(scheduledTime);
    }
}
