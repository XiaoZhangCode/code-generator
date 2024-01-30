package ${basePackage}.cli.command;

import cn.hutool.core.bean.BeanUtil;
import ${basePackage}.cli.util.ReflexUtil;
import ${basePackage}.generator.MainGenerator;
import ${basePackage}.model.DataModel;
import lombok.Data;
import lombok.SneakyThrows;
import picocli.CommandLine;

<#macro generateOption  indent modelInfo>
${indent}@CommandLine.Option(
${indent}names = {<#if modelInfo.abbr??>"-${modelInfo.abbr}" ,</#if><#if modelInfo.fieldName??>"--${modelInfo.fieldName}"</#if>},
${indent}arity = "0..1",
${indent}description = "${modelInfo.description}",
${indent}echo = true,
${indent}interactive = true)
${indent}private ${modelInfo.type} ${modelInfo.fieldName}<#if !forcedInteractiveSwitch><#if modelInfo.defaultValue??>= ${modelInfo.defaultValue?c}</#if></#if>;
</#macro>

<#macro generateCommand indent modelInfo>
${indent}System.out.println("请输入${modelInfo.groupName}配置:");
${indent}CommandLine commandLine = new CommandLine(${modelInfo.type}Command.class);
${indent}commandLine.execute(${modelInfo.allArgsStr});
</#macro>

/**
 * @author ${author}
 * @date ${.now}
 * @description 生成命令
 */
@Data
@CommandLine.Command(name = "generate", mixinStandardHelpOptions = true, description = "生成命令")
public class GenerateCommand implements Runnable {

<#list modelConfig.models.dataModel.filedInfo as modelInfo>

    <#if modelInfo.groupKey??>
    /**
    * ${modelInfo.groupName}
    */
    static DataModel.${modelInfo.type} ${modelInfo.groupKey}  = new DataModel.${modelInfo.type}();

    <#--  根据命令生成分组类  -->
    @CommandLine.Command(name = "${modelInfo.groupKey}")
    @Data
    public static class ${modelInfo.type}Command implements Runnable {
        <#list modelInfo.models as subModelInfo>
        <@generateOption indent="            " modelInfo=subModelInfo />
        </#list >

        @Override
        public void run(){
            <#list modelInfo.models as subModelInfo>
            ${modelInfo.groupKey}.set${subModelInfo.fieldName?cap_first}(${subModelInfo.fieldName});
            </#list>
        }

    }

    <#else >

    <#if modelInfo.description??>
        /**
        * ${modelInfo.description}
        */
    </#if>
    <@generateOption indent="        " modelInfo=modelInfo />
    </#if>
</#list>

    @SneakyThrows
    @Override
    public void run() {
        <#if forcedInteractiveSwitch>
        ReflexUtil.setFieldsWithInteractiveAnnotation(this, this.getClass());
        </#if>

        <#list modelConfig.models.dataModel.filedInfo as modelInfo>
        <#if modelInfo.groupKey??>
        <#if modelInfo.condition??>
        if(${modelInfo.condition}) {
            <@generateCommand indent="        " modelInfo=modelInfo />
        }
        <#else >
        <@generateCommand indent="    " modelInfo=modelInfo />
        </#if>
        </#if>
        </#list>
        DataModel config = new DataModel();
        BeanUtil.copyProperties(this, config);
        <#list modelConfig.models.dataModel.filedInfo as modelInfo>
        <#if modelInfo.groupKey??>
        config.set${modelInfo.groupKey?cap_first}(${modelInfo.groupKey});
        </#if>
        </#list>
        MainGenerator.doGenerate(config);
    }
}
