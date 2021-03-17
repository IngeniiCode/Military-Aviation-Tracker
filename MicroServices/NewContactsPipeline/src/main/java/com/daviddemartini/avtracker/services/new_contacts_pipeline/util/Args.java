package com.daviddemartini.avtracker.services.new_contacts_pipeline.util;

import org.apache.commons.cli.*;

public class Args extends Settings {

    private boolean debug = false;
    private boolean dryrun = false;

    /**
     * Constructor
     *
     * @param args
     * @throws ParseException
     */
    public Args(String[] args) throws ParseException {

        Options options = new Options();

        // define what optiosn are provided
        options.addOption(Option.builder("a")
                .longOpt("msg-host")
                .desc("dump1090 receiver hostname")
                .hasArg()
                .build());

        options.addOption(Option.builder("b")
                .longOpt("msg-port")
                .desc("dump1090 receiver message publisher port")
                .hasArg()
                .build());

        options.addOption(Option.builder("c")
                .longOpt("solr-host")
                .desc("Solr Service hostname")
                .hasArg()
                .build());

        options.addOption(Option.builder("d")
                .longOpt("solr-port")
                .desc("Solr Service port")
                .hasArg()
                .build());

        options.addOption(Option.builder("e")
                .longOpt("solr-core")
                .desc("Solr Service Collection Core Name (eg. 'aviation_contacts'")
                .hasArg()
                .build());

        options.addOption(Option.builder("f")
                .longOpt("debug")
                .desc("Dump all contacts to STDOUT for debugging")
                .build());

        options.addOption(Option.builder("g")
                .longOpt("dryrun")
                .desc("Perform all operations except post to Solr")
                .build());


        // create the parser and the commadline interface
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = new DefaultParser().parse(options, args);

        // check for parameters
        if (cmd.hasOption("msg-host")) {
            dump1090Hostname = cmd.getOptionValue("msg-host");
        }
        if (cmd.hasOption("msg-port")) {
            dump1090Port = Integer.parseInt(cmd.getOptionValue("msg-port"));
        }
        if (cmd.hasOption("solr-host")) {
            solrHostname = cmd.getOptionValue("solr-host");
        }
        if (cmd.hasOption("solr-port")) {
            solrPort = Integer.parseInt(cmd.getOptionValue("solr-port"));
        }
        if (cmd.hasOption("solr-core")) {
            solrCollectionName = cmd.getOptionValue("solr-core");
        }
        if (cmd.hasOption("resolution")) {
            resolution = cmd.getOptionValue("resolution").toLowerCase().trim();
        }
        if (cmd.hasOption("debug")) {
            debug = true;
        }
        if (cmd.hasOption("dryrun")) {
            dryrun = true;
        }

    }

    /**
     * Boolean test to see if the position values are valid, or NaNs
     *
     * @return
     */


    /**
     * Boolean test to see if the display mil only flag has been set.
     *
     * @return
     */

    public boolean hasDebug() {
        return debug;
    }

    public boolean hasDryRun() { return dryrun; }

}
