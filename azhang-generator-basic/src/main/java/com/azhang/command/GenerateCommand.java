package com.azhang.command;

import cn.hutool.core.bean.BeanUtil;
import com.azhang.model.MainTemplateConfig;
import lombok.Data;
import lombok.SneakyThrows;
import picocli.CommandLine;



import static com.azhang.generator.MainGenerator.doGenerate;
import static com.azhang.util.ReflexUtil.setFieldsWithInteractiveAnnotation;

/**
 * @author zhang
 * @date 2023/11/20 13:55
 * @description 生成命令
 */
@Data
@CommandLine.Command(name = "generate", mixinStandardHelpOptions = true, description = "生成命令")
public class GenerateCommand implements Runnable {

    /**
     * 是否生成循环
     */
    @CommandLine.Option(
            names = {"-l", "--loop"},
            arity = "0..1",
            description = "是否生成循环",
            echo = true,
            interactive = true)
    private Boolean loop;

    /**
     * 作者
     */
    @CommandLine.Option(
            names = {"-a", "--author"},
            arity = "0..1",
            description = "作者",
            echo = true,
            interactive = true)
    private String author;

    /**
     * 输出信息
     */
    @CommandLine.Option(
            names = {"-o", "--output"},
            arity = "0..1",
            description = "输出信息",
            echo = true,
            interactive = true)
    private String outputText;


    @SneakyThrows
    @Override
    public void run() {
        setFieldsWithInteractiveAnnotation(this, this.getClass());

        MainTemplateConfig config = new MainTemplateConfig();
        BeanUtil.copyProperties(this, config);
        doGenerate(config);
    }
}
