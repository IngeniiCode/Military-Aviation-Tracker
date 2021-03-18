package com.daviddemartini.avtracker.services.faa.model_reference.handler;

import com.daviddemartini.avtracker.services.faa.model_reference.data.AircraftDataset;
import com.daviddemartini.avtracker.services.faa.model_reference.datamodel.AircraftModel;
import com.daviddemartini.avtracker.services.faa.model_reference.server.RequestHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;


public class JSON extends RequestHandler {

    private static AircraftDataset aircraftDataset;

    public JSON(AircraftDataset aircraftDataset) {
        this.aircraftDataset = aircraftDataset;
    }

    /**
     *
     * @param httpExchange
     * @param modelId
     * @throws IOException
     */
    @Override
    protected void handleResponse(HttpExchange httpExchange, String modelId)  throws  IOException {
        String content = aircraftDataset.getAircraft(modelId).toJSONLite();
        httpExchange.setAttribute("Content-Type", "application/json; charset=UTF-8");
        httpExchange.sendResponseHeaders(200, content.length());

        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(content.getBytes());
        outputStream.flush();
        outputStream.close();
    }


}
