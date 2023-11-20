package com.azhang.example;

import picocli.CommandLine;

/**
 * @author zhang
 * @date 2023/11/20 13:08
 */
@CommandLine.Command(name = "main", description = "sub command", mixinStandardHelpOptions = true)
public class SubCommandExample implements Runnable {
    @Override
    public void run() {
        System.out.println("执行主命令！........");
    }

    @CommandLine.Command(name = "add", description = "增加", mixinStandardHelpOptions = true)
    public static class AddCommand implements Runnable {
        @Override
        public void run() {
            System.out.println("add 执行子命令！........");
        }
    }

    @CommandLine.Command(name = "delete", description = "删除", mixinStandardHelpOptions = true)
    public static class DeleteCommand implements Runnable {
        @Override
        public void run() {
            System.out.println("delete 执行子命令！........");
        }
    }

    @CommandLine.Command(name = "update", description = "修改", mixinStandardHelpOptions = true)
    public static class UpdateCommand implements Runnable {
        @Override
        public void run() {
            System.out.println("update 执行子命令！........");
        }
    }

    @CommandLine.Command(name = "query", description = "查询", mixinStandardHelpOptions = true)
    public static class QueryCommand implements Runnable {
        @Override
        public void run() {
            System.out.println("query 执行子命令！........");
        }
    }

    public static void main(String[] args) {
        String[] myArgs = new String[]{};
        // 查看命令的帮助手册
        myArgs = new String[]{"--help"};

        // 执行增加命令
//        myArgs = new String[]{"add"};

        int execute = new CommandLine(new SubCommandExample())
                .addSubcommand(new AddCommand())
                .addSubcommand(new DeleteCommand())
                .addSubcommand(new UpdateCommand())
                .addSubcommand(new QueryCommand())
                .execute(myArgs);

        System.exit(execute);

    }

}
