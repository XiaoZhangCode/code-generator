package com.azhang.example;

import lombok.Data;
import picocli.CommandLine;
import picocli.CommandLine.*;
import picocli.CommandLine.Command;

/**
 * @author zhang
 * @date 2023/11/20 9:36
 */
@Command(name = "ASCIIArt",version = "ASCII 1.0" ,mixinStandardHelpOptions = true)
@Data
public class ASCIIArt implements Runnable{

    @Option(names = {"-s","--fontSize"},description = "字体大小")
    int fontSize = 10;

    @Parameters(paramLabel = "<word>",defaultValue = "Hello,picocli",
                description = "参数"
    )
    private String[] words = {"Hello","picocli"};
    @Override
    public void run() {
        System.out.println("fontSize = " +  fontSize);
        System.out.println("words = " +  String.join(",",words));
    }


    public static void main(String[] args) {
        int execute = new CommandLine(new ASCIIArt()).execute(args);
        System.exit(execute);
    }


}
