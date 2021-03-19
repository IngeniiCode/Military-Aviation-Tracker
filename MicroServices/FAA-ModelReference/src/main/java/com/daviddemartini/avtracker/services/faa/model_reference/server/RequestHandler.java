package com.daviddemartini.avtracker.services.faa.model_reference.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public abstract class RequestHandler implements HttpHandler {

    private static boolean debug = false;

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        if("GET".equals(httpExchange.getRequestMethod())) {
            String modelId = handleGetRequest(httpExchange);
            handleResponse(httpExchange,modelId);
        }
    }

    /**
     * Get the USGS Data for requested sensor
     * @param httpExchange
     * @return
     */
    private String handleGetRequest(HttpExchange httpExchange) {

        try {
            String modelId = "unknown";

            if(httpExchange.getRequestURI().getQuery() != null){
                String[] args = httpExchange.getRequestURI().getQuery().split("&");
                if(args.length > 0) {
                    for (String tuple : args) {
                        String[] parts = tuple.split("=");
                        if (parts.length > 1) {
                            if(parts[0].toLowerCase().trim().equals("id")){
                                return parts[1].trim();
                            }
                        }
                    }
                }
            }

            return "unknown";
        }
        catch (Exception e) {
            System.out.println("\nException Get Data: " + e);
        }
        return null;
    }

    protected abstract void handleResponse(HttpExchange httpExchange, String modelId)  throws  IOException;
}
