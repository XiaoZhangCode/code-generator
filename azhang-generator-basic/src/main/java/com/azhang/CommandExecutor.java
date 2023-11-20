package com.azhang;

import com.azhang.command.ConfigCommand;
import com.azhang.command.GenerateCommand;
import com.azhang.command.ListCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import static com.azhang.util.ConsoleUtil.getConsoleValue;

/**
 * @author zhang
 * @date 2023/11/20 13:57
 * @description 绑定所有子命令
 */
@Command(name = "zhang", mixinStandardHelpOptions = true, version = "1.0")
public class CommandExecutor implements Runnable {

    private final CommandLine commandLine;

    {
        commandLine = new CommandLine(this)
                .addSubcommand(new GenerateCommand())
                .addSubcommand(new ListCommand())
                .addSubcommand(new ConfigCommand());
    }


    @Override
    public void run() {
        // 不执行子命令时候 打印帮助信息
        commandLine.usage(System.out);
    }

    /**
     * 执行子命令
     *
     * @param args 命令行参数
     */
    public void doExecute(String[] args) {
        commandLine.execute(args);
    }







}
