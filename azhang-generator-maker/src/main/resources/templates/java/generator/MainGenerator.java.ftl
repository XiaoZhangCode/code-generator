package ${basePackage}.generator;

import freemarker.template.TemplateException;
import ${basePackage}.model.DataModel;
import java.io.File;
import java.io.IOException;



<#macro generateFile indent fileInfo >
${indent}inputPath = new File(inputRootPath, "${fileInfo.inputPath}").getAbsolutePath();
${indent}outputPath = new File(outputRootPath, "${fileInfo.outputPath}").getAbsolutePath();
<#if fileInfo.generateType=="dynamic">
${indent}DynamicGenerator.doGenerate(inputPath, outputPath, model);
<#else >
${indent}StaticGenerator.copyFilesByHuTool(inputPath, outputPath);
</#if>
</#macro>


/**
 * @author ${author}
 * @date ${.now}
 * 核心生成器
 */
public class MainGenerator {

    /**
     * 核心生成器
     * @param model model数据模型
     * @throws IOException IOException
     * @throws TemplateException TemplateException
     */
    public static void doGenerate(DataModel model) throws IOException, TemplateException {

        String inputRootPath = "${fileConfig.inputRootPath}";
        String outputRootPath = "${fileConfig.outputRootPath}";


        String inputPath;
        String outputPath;
        <#list modelConfig.models.dataModel.filedInfo as modelInfo>
        <#if modelInfo.groupKey??>
        <#list modelInfo.models as subModelInfo>
        ${subModelInfo.type} ${subModelInfo.fieldName} = model.get${modelInfo.type}().get${subModelInfo.fieldName?cap_first}();
        </#list>
        <#else >
        ${modelInfo.type} ${modelInfo.fieldName} = model.get${modelInfo.fieldName?cap_first}();
        </#if>
        </#list>

        <#list fileConfig.files as fileInfo>
        <#if fileInfo.groupKey??>
        <#if fileInfo.condition??>
        if(${fileInfo.condition}){
        <#list fileInfo.files as subFileInfo>
        <@generateFile indent="            " fileInfo=subFileInfo />
        </#list>
        }
        <#else >
        <#list fileInfo.files as subFileInfo>
        <@generateFile indent="        " fileInfo=subFileInfo />
        </#list>
        </#if>
        <#else >
        <#if fileInfo.condition??>
        if(${fileInfo.condition}){
        <@generateFile indent="              " fileInfo=fileInfo />
        }
        <#else >
        <@generateFile indent="        " fileInfo=fileInfo />
        </#if>
        </#if>
        </#list>
    }
}
