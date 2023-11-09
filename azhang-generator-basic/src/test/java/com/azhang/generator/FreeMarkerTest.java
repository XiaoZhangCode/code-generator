package com.azhang.generator;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhang
 * @date 2023/11/9 19:00
 */
public class FreeMarkerTest {


    public static void main(String[] args) throws IOException, TemplateException {
        // 创建配置对象
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);

        // 指定模版文件的所在路径
        configuration.setDirectoryForTemplateLoading(new File("src/main/resources/templates"));

        // 设置字符集
        configuration.setDefaultEncoding("UTF-8");

        // 创建模板对象，加载指定模板
        Template template = configuration.getTemplate("testWeb.html.ftl");

        // 创建数据模型
        Map<String, Object> model = getStringObjectMap();

        FileWriter out = new FileWriter("testWeb.html");
        template.process(model, out);

        out.close();

    }

    private static Map<String, Object> getStringObjectMap() {
        Map<String, Object> model = new HashMap<>();
        model.put("currentYear", "2023");
        model.put("author", "zhang");
        List<Map<String, Object>> menuItems = new ArrayList<>();
        Map<String, Object> menuItem1 = new HashMap<>();
        menuItem1.put("name", "首页");
        menuItem1.put("url", "/index");
        menuItems.add(menuItem1);
        Map<String, Object> menuItem2 = new HashMap<>();
        menuItem2.put("name", "用户管理");
        menuItem2.put("url", "/user");
        menuItems.add(menuItem2);
        model.put("menuItems", menuItems);
        return model;
    }


}
