package com.daviddemartini.avtracker.services.new_contacts_pipeline.comms.stratux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class SBSTrafficSocket {

    private static final int defaultPort = 30003;
    private static final int socketTimeout = 10;  // timeout socket connection attempt at 10 seconds
    private static final int maxTries = 10; // maximum number of connection attempts
    private String URI;
    private int port;
    private Socket socket;
    private int retryCounter = 0;

    /**
     * Constructor
     *
     * @param URI -- hostname/path to webservice
     * @param port -- integer socket port
     * @throws Exception
     */
    public SBSTrafficSocket(String URI, int port) throws IOException, InterruptedException {
        // setup connection properties
        this.URI = URI;
        this.port = port;
        // initiate socket connection
        connectSBSSocket();
    }
    public SBSTrafficSocket(String URI) throws Exception {
        // not port provided, assume prot 30003
        new SBSTrafficSocket(URI, defaultPort);
    }

    /**
     * Make connection to the socket
     *
     * @throws IOException
     */
    private void connectSBSSocket() throws IOException, InterruptedException {
        try {
            // initiate socket connection
            socket = new Socket();
            socket.connect(new InetSocketAddress(URI, port),( socketTimeout * 1000));
            retryCounter = 0; // reset counter
        }
        catch (SocketTimeoutException stx ){
            // socket timeout
            retryCounter++;
            System.err.printf("ERROR Attempt %d - %s\n",retryCounter,stx.getMessage());
            if(retryCounter < maxTries){
                // implement increasing retry delay
                Thread.sleep((retryCounter * socketTimeout) * 1000);
                connectSBSSocket();
            }
            else {
                throw stx;
            }
        }
        catch (IOException iox) {
            System.err.println("IO Connection Exception: " + iox.getMessage());
            throw iox;
        }
    }


    /**
     * Return a buffered traffic reader
     */
    public BufferedReader getBufferedReader() throws IOException {
        if(socket == null){
            // invalid socket
            throw new RuntimeException(String.format("SBSTrafficSocket - Invalid socket object @ %s:%d",URI,port));
        }
        try {
            return new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch (Exception e) {
            System.err.println("Exception - Unexpected in SBSTraffic Socket Reader " + e.getMessage());
            socket.close();
            return null;
        }
    }

    /**
     * Get socket object
     *
     * @return socket - Socket
     */
    public Socket getSocket() {
        if(socket == null){
            // invalid socket
            throw new RuntimeException(String.format("SBSTrafficSocket - Invalid socket object @ %s:%d",this.URI,this.port));
        }
        return socket;
    }


}
