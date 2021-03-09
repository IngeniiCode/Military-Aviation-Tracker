package com.daviddemartini.stratux.miltracker.util;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;

public class Args {

    private String socketHost = null;
    private int socketPort;
    private double positionLatitude;
    private double positionLongitude;
    private boolean hasLatitude;
    private boolean hasLongitude;
    private double maxRange;

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

        options.addOption(Option.builder("v")
                .longOpt("lat")
                .desc("Position Latitude")
                .hasArg()
                .build());

        options.addOption(Option.builder("h")
                .longOpt("lon")
                .desc("Position Longitude")
                .hasArg()
                .build());

        options.addOption(Option.builder("r")
                .longOpt("range")
                .desc("Maximum Range to Report Contacts")
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
        if (cmd.hasOption("lat")) {
            hasLatitude = true;
            positionLatitude = Double.parseDouble(cmd.getOptionValue("lat"));
        }
        if (cmd.hasOption("lon")) {
            hasLongitude = true;
            positionLongitude = Double.parseDouble(cmd.getOptionValue("lon"));
        }
        if (cmd.hasOption("range")) {
            maxRange = Double.parseDouble(cmd.getOptionValue("range"));
        }

    }

    /**
     * get the socket hostname
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
     * get the socket port
     *
     * @return
     */
    public int getSocketPort(){
        return socketPort;
    }

    /**
     * get the fixed position Latutude, if known
     *
     * @return
     */
    public double getPositionLatitude(){
        return positionLatitude;
    }

    /**
     * get the fixed postion Longitude, if known
     *
     * @return
     */
    public double getPositionLongitude(){
        return positionLongitude;
    }

    /**
     * get max range, if set
     *
     * @return
     */
    public double getMaxRange(){
        return maxRange;
    }

    /**
     * Boolean test to see if the position values are valid, or NaNs
     *
     * @return
     */
    public boolean hasPosition(){
        return (hasLatitude && hasLatitude);
    }
}
