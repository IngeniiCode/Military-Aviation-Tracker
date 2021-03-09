package com.daviddemartini.stratux.miltracker.util;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;

public class Args {

    private String socketHost;
    private int socketPort;

    /**
     * Constructor
     *
     * @param args
     * @throws ParseException
     */
    public Args(String[] args) throws ParseException {

        Options options = new Options();

        // define what optiosn are provided
        options.addOption(Option.builder("h")
                .longOpt("host")
                .desc("Socket hostname")
                .hasArg()
                .build());

        options.addOption(Option.builder("p")
                .longOpt("port")
                .desc("Socket port")
                .hasArg()
                .build());

        // create the parser and the commadline interface
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = new DefaultParser().parse(options,args);

        // check for parameters
        if (cmd.hasOption("host")) {
            socketHost = cmd.getOptionValue("host");
        }
        if (cmd.hasOption("port")) {
            socketPort = Integer.parseInt(cmd.getOptionValue("port"));
        }


    }

    /**
     * get the parsed websocket URL
     *
     * @return
     */
    public String getSocketHost() throws Exception {
        if(this.socketHost != null) {
            return socketHost;
        }
        else {
            Exception e = new IllegalArgumentException("Invalid or missing socket address/url paramter --url");
            throw e ;
        }
    }

    /**
     * Return the socket port
     *
     * @return
     */
    public int getSocketPort(){
        return socketPort;
    }
}
