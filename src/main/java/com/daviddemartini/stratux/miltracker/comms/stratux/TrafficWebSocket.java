package com.daviddemartini.stratux.miltracker.comms.stratux;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TrafficWebSocket {

    private Socket socket;
    private InputStream stream;

    //private static final WebSocketClient client = new StandardWebSocketClient();
    //private static final WebSocketStompClient stompClient = new WebSocketStompClient(client);

    /**
     * Constructor
     *
     * @param URI -- hostname/path to webservice
     * @throws Exception
     */
    public TrafficWebSocket(String URI) throws Exception {
        // not port provided, assume prot 80
        new TrafficWebSocket(URI, 80);
    }
    public TrafficWebSocket(String URI, int port) throws Exception {

        // connect and setup basic socket and stream handlers
        try {
            socket = new Socket(URI, port);
            InputStream input = socket.getInputStream();
        } catch (UnknownHostException e) {
            // Unknown host.. this is likely to be fatal so this can terminate
            throw new UnknownHostException("FATAL: Unable to resolve URI: " + URI);
        } catch (IOException e) {
            // IO Exception -- try to re-establish the connection

        }
    }

    /**
     * Get socket object
     *
     * @return socket - Socket
     */
    public Socket getSocket(){
        return socket;
    }

    /**
     * Get input stream object
     *
     * @return stream - InputStream
     */
    public InputStream getStream(){
        return stream;
    }

}
