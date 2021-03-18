
package com.daviddemartini.avtracker.services.faa.model_reference.server;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class AppServer {

	private HttpServer Server ;
	private static ThreadPoolExecutor threadPoolExecutor;
	private static final int THREADS = 10;
	private String hostname = "localhost";
	private int port = 9201;

	public AppServer(String hostname, int port) throws IOException {
		// set configuration
		this.hostname = hostname;
		this.port = port;

		// set thread pool
		threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREADS);

		// construct server
		System.out.println(" ++ Initiating Service at: " + hostname + ":" + port);
		Server = HttpServer.create(new InetSocketAddress(hostname, port),0);
	}

	/**
	 * Add a handler to the server
	 *
	 * @param context
	 * @param handler
	 */
	public void addHandler(String context, HttpHandler handler){
		System.out.println(" ** Add service listening at " + this.hostname + ":" + this.port + context);
		Server.createContext(context, handler);
	}

	/**
	 * Start up the server
	 *
	 */
	public void start(){
		Server.setExecutor(threadPoolExecutor);
		Server.start();
	}
}
