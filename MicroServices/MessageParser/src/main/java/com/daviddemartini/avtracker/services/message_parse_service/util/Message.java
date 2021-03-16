package com.daviddemartini.avtracker.services.message_parse_service.util;

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
