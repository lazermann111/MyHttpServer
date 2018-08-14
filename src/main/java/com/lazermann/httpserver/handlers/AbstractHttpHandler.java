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

import static com.lazermann.httpserver.Constants.*;

public abstract class AbstractHttpHandler
{
    private Logger logger = LoggerFactory.getLogger(LevelInfoHandler.class);
    protected UserResultRepository resultRepository = new UserResultRepositoryImpl();
    protected static Gson gson = new Gson();

    protected void writeErrorMessage(HttpExchange t, String message) throws IOException
    {
        logger.error(message);
        OutputStream os = t.getResponseBody();
        t.sendResponseHeaders(HTTP_INTERNAL_SERVER_ERROR, message.getBytes().length);
        os.write(message.getBytes());
        os.close();
    }

    protected void writeResponse(HttpExchange t ) throws IOException
    {
        String response = buildResponse(t);
        logger.debug("Response is: " + response);
        t.getResponseHeaders().add("Content-Type", getContentType());
        t.sendResponseHeaders(HTTP_OK_STATUS, response.getBytes().length);

        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    protected abstract boolean validateRequest(HttpExchange t) throws IOException;
    protected abstract String getContentType();

    /**
     * Creates the response
     *
     * @param exchange
     * @return response as string
     */
    protected abstract String buildResponse(HttpExchange exchange);


    /**
     * Creates the response
     *
     * @param uri the uri
     * @param paramName parameter name
     * @return parameter value
     */
    protected String getParameterValue (URI uri, String paramName)
    {
        String query = uri.getQuery();
        if (query != null)
        {
            String[] queryParams = query.split(AND_DELIMITER);
            if (queryParams.length > 0)
            {

                for (String qParam : queryParams)
                {
                    String[] param = qParam.split(EQUAL_DELIMITER);
                    if (param.length > 0)
                    {
                        for (int i = 0; i < param.length; i++)
                        {
                            if (paramName.equalsIgnoreCase(param[i])) {
                                return param[i+1];
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
