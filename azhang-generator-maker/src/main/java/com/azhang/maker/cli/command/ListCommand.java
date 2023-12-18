package com.azhang.maker.cli.command;

import cn.hutool.core.io.FileUtil;
import picocli.CommandLine;

import java.io.File;
import java.util.List;

/**
 * @author zhang
 * @date 2023/11/20 13:56
 * @description 列表命令
 */
@CommandLine.Command(name = "list", description = "查看文件列表", mixinStandardHelpOptions = true)
public class ListCommand implements Runnable {

    @Override
    public void run() {
        String projectPath = System.getProperty("user.dir");
        File parentFile = new File(projectPath).getParentFile();

        File file = new File(parentFile, "azhang-generator-demo-projects/acm-template");
        List<File> files = FileUtil.loopFiles(file);
        for (File f : files) {
            System.out.println(f.getName());
        }
    }
}
