package com.lazermann.httpserver.handlers;

import com.lazermann.httpserver.model.UserResult;
import com.lazermann.httpserver.storage.HazelcastStorage;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import static com.lazermann.httpserver.Constants.METHOD_PUT;
import static com.lazermann.httpserver.Constants.RESULTS_LIST;


public class SetInfoHandler extends AbstractHttpHandler implements HttpHandler
{
    Logger logger = LoggerFactory.getLogger(SetInfoHandler.class);

    private void testResponse()
    {
        List<UserResult> res = HazelcastStorage.getInstance().getList(RESULTS_LIST);


    }



    public void handle(HttpExchange t) throws IOException {

        if(validateRequest(t))
        {
            writeResponse(t);
        }
    }

    @Override
    public String buildResponse(HttpExchange t)
    {
        StringBuilder body = new StringBuilder();
        try (InputStreamReader reader = new InputStreamReader(t.getRequestBody())) {
            char[] buffer = new char[256];
            int read;
            while ((read = reader.read(buffer)) != -1) {
                body.append(buffer, 0, read);
            }
        }
        catch (IOException e)
        {
          return "Cannot read request body";
        }


        UserResult res = gson.fromJson(body.toString(), UserResult.class);
        resultRepository.saveUserResult(res);
        return "Result added successfully ";
    }

    @Override
    public boolean validateRequest(HttpExchange t) throws IOException
    {
        if(!t.getRequestMethod().equals(METHOD_PUT))
        {
            writeErrorMessage(t, "Wrong request method :" + t.getRequestMethod());
            return false;
        }
        if(t.getRequestBody() == null)
        {
            writeErrorMessage(t, "Empty result info");
            return false;
        }
        return true;
    }

    @Override
    protected String getContentType()
    {
        return "text/html";
    }
}
