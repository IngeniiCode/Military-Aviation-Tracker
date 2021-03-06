package com.daviddemartini.stratux.miltracker.util;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;

public class Args {

    private String socketUrl;

    /**
     * Constructor
     *
     * @param args
     * @throws ParseException
     */
    public Args(String[] args) throws ParseException {

        Options options = new Options();

        // define what optiosn are provided
        options.addOption(Option.builder("s")
                .longOpt("websocket")
                .desc("WebSocket URI")
                .hasArg()
                .build());

        // create the parser and the commadline interface
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = new DefaultParser().parse(options,args);

        // check for parameters
        if (cmd.hasOption("websocket")) {
            socketUrl = String.format("ws://%s",cmd.getOptionValue("websocket"));
        }

    }

    /**
     * get the parsed websocket URL
     *
     * @return
     */
    public String getSocketUrl() throws Exception {
        if(this.socketUrl != null) {
            return socketUrl;
        }
        else {
            Exception e = new IllegalArgumentException("Invalid or missing web socket address paramter --websocket");
            throw e ;
        }
    }
}
