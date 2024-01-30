package ${basePackage}.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


<#macro generateModel indent modelInfo>
<#if modelInfo.description??>
${indent}/**
${indent}* ${modelInfo.description}
${indent}*/
</#if>
${indent}private ${modelInfo.type} ${modelInfo.fieldName} <#if modelInfo.defaultValue??>= ${modelInfo.defaultValue?c}</#if>;
</#macro>


/**
 * @author ${author}
 * @date ${.now}
 * ${modelConfig.models.dataModel.description}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataModel {

<#list modelConfig.models.dataModel.filedInfo as modelInfo>
    <#--  有分组  -->
    <#if modelInfo.groupKey??>
    /**
    * ${modelInfo.groupName}
    */
    private ${modelInfo.type} ${modelInfo.groupKey} = new ${modelInfo.type}();

    /**
    * ${modelInfo.description}
    */
    @Data
    public static class ${modelInfo.type} {
        <#list modelInfo.models as subModelInfo>
        <@generateModel indent="        " modelInfo=subModelInfo/>
        </#list>

    }
    <#else >
    <#--  无分组  -->
    <@generateModel indent="    " modelInfo=modelInfo/>
    </#if>
</#list>

}
