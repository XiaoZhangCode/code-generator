package com.azhang.web.service.impl;


import cn.hutool.extra.mail.MailUtil;
import com.azhang.web.service.MailService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class MailServiceImplTest {


    @Resource
    private MailService mailServiceImpl;

    @Test
    public void sendMail() {
        System.out.println(mailServiceImpl.sendMail("2795524024@qq.com", "测试", "测试"));

    }
}