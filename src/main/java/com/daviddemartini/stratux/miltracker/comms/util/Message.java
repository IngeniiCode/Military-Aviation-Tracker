package com.daviddemartini.stratux.miltracker.comms.util;

import java.nio.ByteBuffer;

public class Message {

    /**
     * ByteBuffer conversion utility used for writing Ascii-text messages to ports.
     *
     * @param message
     * @return
     */
    public static ByteBuffer Ascii(String message){
        return ByteBuffer.wrap((message + "\n").getBytes());
    }
}
