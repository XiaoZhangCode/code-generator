package com.azhang.maker;


import com.azhang.maker.generator.MainGenerator;
import freemarker.template.TemplateException;

import java.io.IOException;

/**
 * @author zhang
 * @date 2023/11/9 17:56
 */
public class Main {
    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
        MainGenerator generator = new MainGenerator();
        generator.doGenerate();
    }
}
