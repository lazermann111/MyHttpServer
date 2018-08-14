package com.lazermann.httpserver.handlers;


import com.lazermann.httpserver.model.UserResult;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.List;

import static com.lazermann.httpserver.Constants.*;

public class LevelInfoHandler extends AbstractHttpHandler implements HttpHandler
{
    Logger logger = LoggerFactory.getLogger(LevelInfoHandler.class);


    public void handle(HttpExchange t) throws IOException
    {
      if(validateRequest(t))
      {
          writeResponse(t);
      }

    }

    @Override
    public boolean validateRequest(HttpExchange t) throws IOException
    {

        if(!t.getRequestMethod().equals(METHOD_GET))
        {
            writeErrorMessage(t, "Wrong request method :" + t.getRequestMethod());
            return false;
        }
        if(t.getRequestURI().getQuery() == null || t.getRequestURI().getQuery().isEmpty())
        {
            writeErrorMessage(t, "Empty level id");
            return false;
        }

        return true;
    }

    @Override
    public String buildResponse(URI uri)
    {
       String levelId = getParameterValue(uri, LEVEL_ID);
       List<UserResult> res = resultRepository.getTopLevelInfo(levelId);
       return gson.toJson(res);
    }
}
