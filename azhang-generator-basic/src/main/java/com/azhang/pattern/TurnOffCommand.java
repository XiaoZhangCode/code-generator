package com.azhang.pattern;

import lombok.Data;

/**
 * @author zhang
 * @date 2023/11/20 13:40
 */
@Data
public class TurnOffCommand implements Command{

    private Device device;

    public TurnOffCommand(Device device) {
        this.device = device;
    }

    @Override
    public void execute() {
        device.turnOff();
    }
}
