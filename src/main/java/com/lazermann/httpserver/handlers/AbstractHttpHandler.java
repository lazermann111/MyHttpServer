package com.lazermann.httpserver.handlers;


import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public abstract class AbstractHttpHandler
{
    Logger logger = LoggerFactory.getLogger(LevelInfoHandler.class);

    protected static Gson gson = new Gson();


    public void writeErrorMessage(HttpExchange t, String message) throws IOException
    {

        logger.error(message);
        OutputStream os = t.getResponseBody();
        os.write(message.getBytes());
        os.close();
    }

    public abstract boolean validateRequest(HttpExchange t) throws IOException;

    /**
     * Creates the response from query params.
     *
     * @param uri the uri
     * @return the string
     */
    public abstract String createResponseFromQueryParams(URI uri);


}
