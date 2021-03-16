package com.daviddemartini.avtracker.services.message_parse_service.comms.stratux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class SBSTrafficSocket {

    private String URI;
    private int port;
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
    public SBSTrafficSocket(String URI) throws Exception {
        // not port provided, assume prot 30003
        new SBSTrafficSocket(URI, 30003);
    }

    public SBSTrafficSocket(String URI, int port) throws Exception {
        this.URI = URI;
        this.port = port;
        connectSBSSocket();
    }

    /**
     * Read traffic from the input port
     */
    public void readTraffic() throws IOException {
        BufferedReader bis = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        String inputLine;
        while ((inputLine = bis.readLine()) != null) {
            System.out.println(inputLine);
        }
        // done, close socket
        socket.close();
    }

    /**
     * Make connection to the socket
     *
     * @throws IOException
     */
    private void connectSBSSocket() throws IOException {
        this.socket = new Socket(this.URI, this.port);
    }

    /**
     * Get socket object
     *
     * @return socket - Socket
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * Get input stream object
     *
     * @return stream - InputStream
     */
    public InputStream getStream() {
        return stream;
    }

}
