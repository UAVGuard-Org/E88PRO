package com.uavguard.wrj12620;

import com.uavguard.sdk.Video;
import java.io.ByteArrayOutputStream;
import java.net.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.nio.charset.StandardCharsets;
import java.util.function.Consumer;

public class E88PRO_Video implements Video {

    private Consumer<byte[]> callback;
    private int port = 7099;
    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private boolean writing = false;

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public void getSetup(DatagramSocket socket) throws Exception {
        byte[] resumeBytes = "Bv".getBytes(StandardCharsets.UTF_8);

        DatagramPacket resume = new DatagramPacket(
            resumeBytes,
            resumeBytes.length
        );

        socket.send(resume);
    }

    @Override
    public void getLoop(DatagramSocket socket, byte[] data) throws Exception {
        for (int i = 0; i < data.length - 1; i++) {
            if (data[i] == (byte) 0xFF && data[i + 1] == (byte) 0xD8) {
                buffer.reset();
                buffer.write(0xFF);
                buffer.write(0xD8);
                writing = true;
                i++;
                continue;
            }

            if (
                writing && data[i] == (byte) 0xFF && data[i + 1] == (byte) 0xD9
            ) {
                buffer.write(0xFF);
                buffer.write(0xD9);

                if (callback != null) callback.accept(buffer.toByteArray());

                writing = false;
                buffer.reset();
                i++;
                continue;
            }

            if (writing) {
                buffer.write(data[i]);
            }
        }
    }

    @Override
    public void setCallback(Consumer<byte[]> callback) {
        this.callback = callback;
    }
}
