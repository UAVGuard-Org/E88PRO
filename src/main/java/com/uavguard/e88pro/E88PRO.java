package com.uavguard.e88pro;

import com.uavguard.plugin.Action;
import com.uavguard.plugin.Command;
import com.uavguard.plugin.Plugin;

public class E88PRO implements Plugin {

    private final byte[] packet = {
        (byte) 0x03,
        (byte) 0x66,
        (byte) 0x80,
        (byte) 0x80,
        (byte) 0x80,
        (byte) 0x80,
        (byte) 0x00,
        (byte) 0x00,
        (byte) 0x99,
    };

    public int getPort() {
        return 7099;
    }

    public String getName() {
        return "e88pro";
    }

    public byte[] getPacket() {
        return packet;
    }

    public Command[] getCommands() {
        return new Command[] {
            new Command(
                "Decolar",
                new byte[] {
                    (byte) 0x03,
                    (byte) 0x66,
                    (byte) 0x80,
                    (byte) 0x80,
                    (byte) 0x80,
                    (byte) 0x80,
                    (byte) 0x01,
                    (byte) 0x01,
                    (byte) 0x99,
                }
            ),
            new Command(
                "Pousar",
                new byte[] {
                    (byte) 0x03,
                    (byte) 0x66,
                    (byte) 0x80,
                    (byte) 0x80,
                    (byte) 0x80,
                    (byte) 0x80,
                    (byte) 0x02,
                    (byte) 0x02,
                    (byte) 0x99,
                }
            ),
        };
    }

    public void setParameter(Action action, int percent) {
        byte value = (byte) (128 +
            (percent / 100f) * (percent >= 0 ? 127 : 128));

        switch (action) {
            case ROLL -> packet[2] = value;
            case PITCH -> packet[3] = value;
            case THROTTLE -> packet[4] = value;
            case YAW -> packet[5] = value;
        }

        int checksum = 0;
        for (int i = 2; i <= 5; i++) {
            checksum ^= packet[i];
        }
        packet[7] = (byte) checksum;
    }
}
