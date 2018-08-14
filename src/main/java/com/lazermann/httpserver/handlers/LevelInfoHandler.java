package com.lazermann.httpserver.handlers;


import com.lazermann.httpserver.model.UserResult;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;

import static com.lazermann.httpserver.Constants.LEVEL_ID;
import static com.lazermann.httpserver.Constants.METHOD_GET;

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
    protected String getContentType()
    {
        return "application/json";
    }

    @Override
    public String buildResponse(HttpExchange t)
    {
       String levelId = getParameterValue(t.getRequestURI(), LEVEL_ID);
       Collection<UserResult> res = resultRepository.getTopLevelInfo(levelId);
       return gson.toJson(res);
    }
}
