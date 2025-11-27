package com.uavguard.wrj12620;

import com.uavguard.sdk.Action;
import com.uavguard.sdk.Command;
import com.uavguard.sdk.Plugin;
import com.uavguard.sdk.Video;

public class WRJ12620 implements Plugin {

    private final WRJ12620_Command command = new WRJ12620_Command();
    private final WRJ12620_Video video = new WRJ12620_Video();

    public String getName() {
        return "wrj12620";
    }

    public String getVersion() {
        return "1.0.0";
    }

    public Command getCommand() {
        return command;
    }

    public Video getVideo() {
        return video;
    }
}
