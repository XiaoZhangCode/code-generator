package com.azhang.web.manager;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class CosManagerTest {
    @Resource
    private CosManager cosManager;

    @Test
    public void deleteObject() {
        cosManager.deleteObject("/generator_make_template/1/5T88KEyv-acm-template-pro.zip");
    }

    @Test
    public void deleteObjects() {

    }

    @Test
    public void deleteDir() {
    }
}