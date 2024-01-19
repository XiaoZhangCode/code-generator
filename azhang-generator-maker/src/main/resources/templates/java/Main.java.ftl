package ${basePackage};


import ${basePackage}.cli.CommandExecutor;

/**
 * @author ${author}
 * @date ${.now}
 */
public class Main {
    public static void main(String[] args) {
        CommandExecutor commandExecutor = new CommandExecutor();
        commandExecutor.doExecute(args);
    }
}