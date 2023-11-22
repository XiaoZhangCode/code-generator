package com.azhang.cli.command;

import cn.hutool.core.util.ReflectUtil;
import com.azhang.model.MainTemplateConfig;
import picocli.CommandLine;

import java.lang.reflect.Field;

/**
 * @author zhang
 * @date 2023/11/20 13:55
 * @description 配置命令
 */
@CommandLine.Command(name = "config", description = "配置命令",mixinStandardHelpOptions = true)
public class ConfigCommand implements Runnable{

    @Override
    public void run() {
        Field[] fields =
                ReflectUtil.getFields(MainTemplateConfig.class);
        for (Field field : fields) {
            System.out.println(field.getName() + " : " + field.getType().getName());
        }
    }
}
