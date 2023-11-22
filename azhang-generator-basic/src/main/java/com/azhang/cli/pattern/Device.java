package com.azhang.cli.pattern;

import lombok.Data;

/**
 * @author zhang
 * @date 2023/11/20 13:41
 * @description 设备类
 */
@Data
public class Device {
    public Device(String name) {
        this.name = name;
    }

    private String name;

    public void turnOn() {
        System.out.println(name + " 设备开启");
    }
    public void turnOff() {
        System.out.println(name + " 设备关闭");
    }


}
