package com.lazermann.httpserver.handlers;

import com.google.gson.JsonSyntaxException;
import com.lazermann.httpserver.model.UserResult;
import com.lazermann.httpserver.storage.HazelcastStorage;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import static com.lazermann.httpserver.Constants.*;


public class SetInfoHandler extends AbstractHttpHandler implements HttpHandler
{
    Logger logger = LoggerFactory.getLogger(SetInfoHandler.class);

    private void testResponse()
    {
        List<UserResult> res = HazelcastStorage.getInstance().getList(RESULTS_LIST);


    }


    //todo Должны выводиться и храниться только топ результаты. -- means that we need to implement "LRU cache"
    public void handle(HttpExchange t) throws IOException {

        if(validateRequest(t))
        {
            writeResponse(t);
        }
    }

    @Override
    public String createResponseFromQueryParams(URI uri)
    {
        UserResult res = gson.fromJson(uri.getPath().replace(URI_SETINFO, ""), UserResult.class);
        resultRepository.saveUserResult(res);
        return ""; //todo!
    }

    @Override
    public boolean validateRequest(HttpExchange t) throws IOException
    {
        if(!t.getRequestMethod().equals(METHOD_PUT))
        {
            writeErrorMessage(t, "Wrong request method :" + t.getRequestMethod());
            return false;
        }
        if(t.getRequestURI().getPath().replace(URI_SETINFO, "").isEmpty())
        {
            writeErrorMessage(t, "Empty result info");
            return false;
        }
        try
        {
            UserResult res =   gson.fromJson(t.getRequestURI().getPath().replace(URI_SETINFO, ""), UserResult.class);
            return true;
        }
        catch (JsonSyntaxException e)
        {
            writeErrorMessage(t, "Cannot parse result info");
            return false;
        }
    }
}
