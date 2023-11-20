package com.azhang.command;

import cn.hutool.core.bean.BeanUtil;
import com.azhang.model.MainTemplateConfig;
import lombok.Data;
import lombok.SneakyThrows;
import picocli.CommandLine;


import static com.azhang.generator.MainGenerator.doGenerate;

/**
 * @author zhang
 * @date 2023/11/20 13:55
 * @description 生成命令
 */
@Data
@CommandLine.Command(name = "generate", mixinStandardHelpOptions = true, description = "生成命令")
public class GenerateCommand implements Runnable{

    /**
     * 是否生成循环
     */
    @CommandLine.Option(
            names = {"-l","--loop"},
            arity = "0..1",
            description = "是否生成循环",
            echo = true,
            interactive = true)
    private boolean loop = false;

    /**
     * 作者
     */
    @CommandLine.Option(
            names = {"-a","--author"},
            arity = "0..1",
            description = "作者",
            echo = true,
            interactive = true)
    private String author = "zhang";

    /**
     * 输出信息
     */
    @CommandLine.Option(
            names = {"-o","--output"},
            arity = "0..1",
            description = "输出信息",
            echo = true,
            interactive = true)
    private String outputText = "sum = ";



    @SneakyThrows
    @Override
    public void run(){
        MainTemplateConfig config = new MainTemplateConfig();
        BeanUtil.copyProperties(this, config);
        doGenerate(config);
    }
}
