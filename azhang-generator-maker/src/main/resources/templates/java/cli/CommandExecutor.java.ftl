package ${basePackage}.cli;

import ${basePackage}.cli.command.ConfigCommand;
import ${basePackage}.cli.command.GenerateCommand;
import ${basePackage}.cli.command.ListCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

/**
 * @author ${author}
 * @date ${.now}
 * @description 绑定所有子命令
 */
@Command(name = "${name}", mixinStandardHelpOptions = true, version = "1.0")
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
