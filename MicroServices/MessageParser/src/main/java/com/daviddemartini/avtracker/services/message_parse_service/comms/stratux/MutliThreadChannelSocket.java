package com.daviddemartini.avtracker.services.message_parse_service.comms.stratux;

import com.daviddemartini.avtracker.services.message_parse_service.util.Message;
import com.daviddemartini.avtracker.services.message_parse_service.datamodel.AircraftSBS;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class MutliThreadChannelSocket {

    private static String connectionString;
    private final Selector selector;
    ServerSocketChannel serverSocketChannel;
    private SelectionKey selectionKey;

    /**
     * Constructor
     *
     * Expects a port to be passed, but will use the pre-configured
     * default if it is not
     *
     * @param feedSocketPort - integer port number to bind
     */
    public MutliThreadChannelSocket(String feedSocketHost, int feedSocketPort) throws IOException {
        // setup the socket channel
        InetAddress host = InetAddress.getByName(feedSocketHost);
        this.connectionString = String.format("%s:%d",host.toString(),feedSocketPort);
        System.out.printf("\tMutliThreadChannelSocket listen '%s'\n",this.connectionString);
        this.selector = Selector.open();
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.configureBlocking(false);
        this.serverSocketChannel.bind(new InetSocketAddress(host, feedSocketPort));
        this.serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
        this.selectionKey = null;
    }

    /**
     * publish contact data to client(s)
     *
     * @param contact
     */
    public boolean publishContact(AircraftSBS contact) {
        // express contacts detected within certain range in JSON with any known data.
        try {
            return writeToSocket(contact);
        } catch (Exception e) {
            System.err.printf("MutliThreadChannelSocket [%d] ERROR -- %s\n",this.connectionString, e.getMessage());
        }
        return false;
    }

    /**
     * writeToSocket port, handling requests...
     *
     * @param contact -- an AircraftSBS contact object
     * @throws IOException
     */
    private boolean writeToSocket(AircraftSBS contact) throws IOException {
        // if no clients connected to the selector, nothing to do
        if (this.selector.selectNow() <= 0) {
            return false;
        }
        // select keys for connected clients
        Set<SelectionKey> selectedKeys = this.selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectedKeys.iterator();
        while (iterator.hasNext()) {
            this.selectionKey = iterator.next();
            iterator.remove();
            if (this.selectionKey.isAcceptable()) {
                SocketChannel socketConnect = this.serverSocketChannel.accept();
                socketConnect.configureBlocking(false);
                socketConnect.register(selector, SelectionKey.OP_WRITE);
                System.out.printf("\ta %s reader CONNECTED\n", this.getClass().getName());
            }
            try {
                if (selectionKey.isWritable()) {
                    // write to the socket!
                    SocketChannel writeChannel = (SocketChannel) selectionKey.channel();
                    writeChannel.write(Message.Ascii(contact.toJSONLite()));
                }
            } catch (Exception IOException) {
                // looks like port has gone away.. de-register
                System.out.printf("\t%s reader DROPPED\n", this.getClass().getName());
                selectionKey.channel().close();
            }
        }
        return true;
    }
}
