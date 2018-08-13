package com.lazermann.httpserver.handlers;

import com.lazermann.httpserver.model.UserResult;
import com.lazermann.httpserver.storage.HazelcastStorage;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.List;

import static com.lazermann.httpserver.Constants.RESULTS_LIST;


public class SetInfoHandler implements HttpHandler
{

    private int i;
    private void testResponse()
    {
        /*Map<String,UserResult> res2 = HazelcastStorage.getInstance().getMap(RESULTS_LIST);
        res2.put("a", new UserResult(1,2 + i,3 + i));*/

        List<UserResult> res = HazelcastStorage.getInstance().getList(RESULTS_LIST);
        res.add(new UserResult(1,2 + i,3 + i));
        res.add(new UserResult(1,1 + i,30+ i));
        res.add(new UserResult(1,3 + i,50+ i));

        i++;
        res.add(new UserResult(4,5+ i,6+ i));

    }
    public void handle(HttpExchange httpExchange) throws IOException {

        testResponse();
    }
}
