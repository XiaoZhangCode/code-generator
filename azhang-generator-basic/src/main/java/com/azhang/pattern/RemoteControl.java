package com.azhang.pattern;

/**
 * @author zhang
 * @date 2023/11/20 13:47
 * @description 远程控制
 */
public class RemoteControl {

    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    /**
     * 按钮被按下
     */
    public void pressButton() {
        command.execute();
    }



}
