package com.lazermann.httpserver.handlers;

import com.google.gson.Gson;
import com.lazermann.httpserver.model.UserResult;
import com.lazermann.httpserver.storage.HazelcastStorage;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.security.InvalidParameterException;
import java.util.List;

import static com.lazermann.httpserver.Constants.HTTP_OK_STATUS;
import static com.lazermann.httpserver.Constants.RESULTS_LIST;


//@SuppressWarnings("restriction")
public class UserInfoHandler implements HttpHandler {



    private List<UserResult> testResponse()
    {

       /* List<UserResult> a = new ArrayList<UserResult>();

        a.add(new UserResult(1,2,3));
        a.add(new UserResult(4,5,6));*/


        return HazelcastStorage.getInstance().getList(RESULTS_LIST);
    }

    public void handle(HttpExchange t) throws IOException {

        //Create a response form the request query parameters
        URI uri = t.getRequestURI();

        //todo validate request method
        if(!t.getRequestMethod().equals("GET"))
            throw new InvalidParameterException("Wrong request method !");

        String response = createResponseFromQueryParams(uri);
        System.out.println("Response: " + response);
        //Set the response header status and length
        t.getResponseHeaders().add("Content-Type", "application/json");
        t.sendResponseHeaders(HTTP_OK_STATUS, response.getBytes().length);

        System.out.println("t = " + t);

        //Write the response string
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();



    }

    /**
     * Creates the response from query params.
     *
     * @param uri the uri
     * @return the string
     */
    private String createResponseFromQueryParams(URI uri) {
        //todo add in-mem
        //todo validate params
        Gson gson = new Gson();
        List<UserResult> res = testResponse();
        return gson.toJson(res);
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
}