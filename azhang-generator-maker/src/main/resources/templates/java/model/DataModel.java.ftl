package ${basePackage}.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    <#if modelInfo.description??>
    /**
    * ${modelInfo.description}
    */
    </#if>
    private ${modelInfo.type} ${modelInfo.fieldName} <#if modelInfo.defaultValue??>= ${modelInfo.defaultValue?c}</#if>;
</#list>

}
