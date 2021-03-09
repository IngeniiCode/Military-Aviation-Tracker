package com.daviddemartini.stratux.miltracker;

import com.daviddemartini.stratux.miltracker.comms.stratux.SBSTrafficSocket;
import com.daviddemartini.stratux.miltracker.util.Args;

public class StratuxMilTracker {

    public static void main (String[] args){
        // process the CL args..
        try {
            // Process args from commandline
            Args clArgs = new Args(args);
            // Setup stream reader
            SBSTrafficSocket sbsTrafficSocket = new SBSTrafficSocket(clArgs.getSocketHost(), clArgs.getSocketPort());

            // basic call to read messages from the port --
            sbsTrafficSocket.readTraffic();


        }
        catch (Exception e) {
            System.err.printf("\n** Fatal Error -- %s\n",e.toString());
            System.exit(9);
        }

    }
}
