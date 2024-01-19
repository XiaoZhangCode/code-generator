package ${basePackage}.generator;

import cn.hutool.core.io.FileUtil;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;

/**
 * @author ${author}
 * @date ${.now}
 * 动态文件生成器
 */
public class DynamicGenerator {

    /**
     * 动态生成文件
     *
     * @param inputPath  模板文件路径
     * @param outputPath 输出文件路径
     * @param model      数据模型
     */
    public static void doGenerate(String inputPath, String outputPath, Object model) throws IOException, TemplateException {
        // 1.获取配置对象
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);
        // 2. 指定模板文件所在的路径
        File templateDir = new File(inputPath).getParentFile();
        configuration.setDirectoryForTemplateLoading(templateDir);
        // 3. 设置字符编码
        configuration.setDefaultEncoding("UTF-8");
        configuration.setEncoding(Locale.getDefault(), "UTF-8");
        // 4. 创建模板对象，并设置模板文件
        Template template = configuration.getTemplate(new File(inputPath).getName(), "UTF-8");
        // 文件不存在则创建文件和父目录
        if (!FileUtil.exist(outputPath)) {
            FileUtil.touch(outputPath);
        }
        // 5. 输出文件
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get(outputPath)), StandardCharsets.UTF_8));
        template.process(model, out);
        // 6. 关闭流
        out.close();
    }


}
