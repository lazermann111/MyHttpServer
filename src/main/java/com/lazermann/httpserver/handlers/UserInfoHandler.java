package com.lazermann.httpserver.handlers;

import com.lazermann.httpserver.model.UserResult;
import com.lazermann.httpserver.storage.HazelcastStorage;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.List;

import static com.lazermann.httpserver.Constants.*;


public class UserInfoHandler extends AbstractHttpHandler implements HttpHandler
{
    Logger logger = LoggerFactory.getLogger(UserInfoHandler.class);


    private List<UserResult> testResponse()
    {
        return HazelcastStorage.getInstance().getList(RESULTS_LIST);
    }

    public void handle(HttpExchange t) throws IOException {


        URI uri = t.getRequestURI();
        validateRequest(t);

            String response = createResponseFromQueryParams(uri);
            logger.info("Response: " + response);
            //Set the response header status and length
            t.getResponseHeaders().add("Content-Type", "application/json");
            t.sendResponseHeaders(HTTP_OK_STATUS, response.getBytes().length);

            logger.debug("t = " + t);

            //Write the response string
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();

    }



    private static void printRequestInfo(HttpExchange exchange) {
        System.out.println("-- headers --");
        Headers requestHeaders = exchange.getRequestHeaders();
        requestHeaders.entrySet().forEach(System.out::println);



        System.out.println("-- HTTP method --");
        String requestMethod = exchange.getRequestMethod();
        System.out.println(requestMethod);

        System.out.println("-- query --");
        URI requestURI = exchange.getRequestURI();
        String query = requestURI.getQuery();
        System.out.println(query);
    }

    @Override
    public boolean validateRequest(HttpExchange t) throws IOException
    {
        if(!t.getRequestMethod().equals(METHOD_GET))
        {
            writeErrorMessage(t, "Wrong request method :" + t.getRequestMethod());
            return false;
        }
        if(t.getRequestURI().getPath().replace(URI_USERINFO, "").isEmpty())
        {
            writeErrorMessage(t, "Empty user id");
            return false;
        }

        return true;
    }

    @Override
    public String createResponseFromQueryParams(URI uri) {
        return null;
    }
}