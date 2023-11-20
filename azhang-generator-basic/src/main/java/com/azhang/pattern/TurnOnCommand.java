package com.azhang.pattern;

import lombok.Data;

/**
 * @author zhang
 * @date 2023/11/20 13:46
 */
@Data
public class TurnOnCommand implements Command{

    private Device device;

    public TurnOnCommand(Device device) {
        this.device = device;
    }
    @Override
    public void execute() {
        device.turnOn();
    }
}
