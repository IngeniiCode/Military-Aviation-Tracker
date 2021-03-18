package com.daviddemartini.avtracker.services.faa.model_reference;

import com.daviddemartini.avtracker.services.faa.model_reference.data.AircraftDataset;
import com.daviddemartini.avtracker.services.faa.model_reference.handler.JSON;
import com.daviddemartini.avtracker.services.faa.model_reference.server.AppServer;
import com.daviddemartini.avtracker.services.faa.model_reference.util.Args;
import org.apache.commons.cli.ParseException;


/**
 * FAA Sourced Aircraft Model Map
 */
public class ModelReference {

	private static Args clArgs;
	private static String publisherHost;
	private static int publisherPort;
	private static String faaAircraftModelFile;
	private static AppServer Server;

	public static void main(String[] args){

		// process the CL args..
		try {
			// Process args from commandline
			setupApplication(args);

			System.out.println("\nFAA Model Database");
			String dataFileLocation = String.format("%s/%s",System.getProperty("user.dir"),faaAircraftModelFile);

			AircraftDataset aircraftDataset = new AircraftDataset(dataFileLocation);

			// Setup REST API routes
			Server = new AppServer(publisherHost,publisherPort);
			Server.addHandler("/json", new JSON(aircraftDataset));

			// Start server
			Server.start();

		}
		catch (Exception e){
			System.err.println("Fatal Error : " + e.getMessage());
			System.exit(9);
		}

	}

	/**
	 * Handle Application setup
	 * <p>
	 * Compute resolution from settings, if provided
	 *
	 * @param args
	 * @throws ParseException
	 */
	private static void setupApplication(String[] args) throws ParseException {
		// store into args var
		clArgs = new Args(args);

		publisherHost = clArgs.getPublisherHostname();
		publisherPort = clArgs.getPublisherPort();
		faaAircraftModelFile = clArgs.getFaaDataFile();

	}

}
