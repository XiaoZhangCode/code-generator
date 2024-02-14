package ${basePackage}.cli.command;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import ${basePackage}.cli.util.ReflexUtil;
import ${basePackage}.generator.MainGenerator;
import ${basePackage}.model.DataModel;
import lombok.Data;
import lombok.SneakyThrows;
import picocli.CommandLine;


/**
 * @author ${author}
 * @date ${.now}
 * @description 读取json生成命令
 */
@Data
@CommandLine.Command(name = "json-generate", mixinStandardHelpOptions = true, description = "读取json生成命令")
public class JsonGenerateCommand implements Runnable {


    /**
     * json文件路径
     */
    @CommandLine.Option(
            names = {"-f", "--file"},
            arity = "0..1",
            description = "json文件路径",
            echo = true,
            interactive = true)
    private String filePath;


    @SneakyThrows
    @Override
    public void run() {
        <#if forcedInteractiveSwitch>
        ReflexUtil.setFieldsWithInteractiveAnnotation(this, this.getClass());
        </#if>

        String dataModelStr = FileUtil.readUtf8String(filePath);
        DataModel dataModel = JSONUtil.toBean(dataModelStr, DataModel.class);
        MainGenerator.doGenerate(dataModel);
    }
}
