package com.daviddemartini.avtracker.services.faa.model_reference;

import com.daviddemartini.avtracker.services.faa.model_reference.datamodel.AircraftModel;
import com.daviddemartini.avtracker.services.faa.model_reference.util.Args;
import org.apache.commons.cli.ParseException;

import java.util.HashMap;
import java.util.Map;

/**
 * FAA Sourced Aircraft Model Map
 */
public class ModelReference {

	private static final Map<String, AircraftModel> AircraftModel = new HashMap<>();
	private static Args clArgs;
	private static String publisherHost;
	private static int publisherPort;
	private static String faaAircraftModelFile;

	public static void main(String[] args){

		// process the CL args..
		try {
			// Process args from commandline
			setupApplication(args);

			System.out.println("\nFAA Model Database");
			System.out.println("\tInput file :" + faaAircraftModelFile);
			System.out.println("\tService Hostname: " + publisherHost);
			System.out.println("\tService Port: " + publisherPort);

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
