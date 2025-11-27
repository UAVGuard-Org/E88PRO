package com.uavguard.wrj12620;

import com.uavguard.sdk.Action;
import com.uavguard.sdk.Command;
import com.uavguard.sdk.Movement;

public class E88PRO_Command implements Command {

    private final byte[] packet = {
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
        return 8090;
    }

    public String getName() {
        return "wrj12620";
    }

    public byte[] getPacket() {
        return packet;
    }

    public Action[] getActions() {
        return new Action[] {
            new Action(
                "Decolar",
                new byte[] {
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
            new Action(
                "Pousar",
                new byte[] {
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

    public void setParameter(Movement action, int percent) {
        byte value = (byte) (128 +
            (percent / 100f) * (percent >= 0 ? 127 : 128));

        switch (action) {
            case ROLL -> packet[1] = value;
            case PITCH -> packet[2] = value;
            case THROTTLE -> packet[3] = value;
            case YAW -> packet[4] = value;
        }

        int checksum = 0;
        for (int i = 1; i <= 4; i++) {
            checksum ^= packet[i];
        }
        packet[6] = (byte) checksum;
    }
}
