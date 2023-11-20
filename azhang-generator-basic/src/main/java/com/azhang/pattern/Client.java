package com.azhang.pattern;

/**
 * @author zhang
 * @date 2023/11/20 13:48
 * @description 客户端
 */
public class Client {

    public static void main(String[] args) {
        // 创建设备
        Device tv = new Device("TV");
        Device stereo = new Device("stereo");

        // 创建命令
        TurnOffCommand turnOffCommand = new TurnOffCommand(tv);
        TurnOnCommand turnOnCommand = new TurnOnCommand(stereo);

        // 创建调用者
        RemoteControl remoteControl = new RemoteControl();

        // 执行命令
        remoteControl.setCommand(turnOnCommand);
        remoteControl.pressButton();

    }
}
