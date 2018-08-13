package com.lazermann.httpserver.handlers;


import com.google.gson.Gson;
import com.lazermann.httpserver.repositories.UserResultRepository;
import com.lazermann.httpserver.repositories.UserResultRepositoryImpl;
import com.sun.net.httpserver.HttpExchange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

import static com.lazermann.httpserver.Constants.HTTP_OK_STATUS;

public abstract class AbstractHttpHandler
{
    Logger logger = LoggerFactory.getLogger(LevelInfoHandler.class);


    protected UserResultRepository resultRepository = new UserResultRepositoryImpl(); //todo

    protected static Gson gson = new Gson();

    protected void writeErrorMessage(HttpExchange t, String message) throws IOException
    {

        logger.error(message);
        OutputStream os = t.getResponseBody();
        os.write(message.getBytes());
        os.close();
    }

    protected void writeResponse(HttpExchange t ) throws IOException
    {

        String response = createResponseFromQueryParams(t.getRequestURI());
        t.getResponseHeaders().add("Content-Type", "application/json");
        t.sendResponseHeaders(HTTP_OK_STATUS, response.getBytes().length);

        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    protected abstract boolean validateRequest(HttpExchange t) throws IOException;

    /**
     * Creates the response from query params.
     *
     * @param uri the uri
     * @return response as string
     */
    protected abstract String createResponseFromQueryParams(URI uri);

}
