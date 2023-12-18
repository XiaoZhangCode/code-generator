package com.azhang.maker.cli.command;

import cn.hutool.core.bean.BeanUtil;
import com.azhang.maker.cli.util.ReflexUtil;
import com.azhang.maker.generator.file.FileGenerator;
import com.azhang.maker.model.DataModel;
import lombok.Data;
import lombok.SneakyThrows;
import picocli.CommandLine;

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
        ReflexUtil.setFieldsWithInteractiveAnnotation(this, this.getClass());

        DataModel config = new DataModel();
        BeanUtil.copyProperties(this, config);
        FileGenerator.doGenerate(config);
    }
}
