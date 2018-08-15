package com.lazermann.httpserver;


import com.lazermann.httpserver.handlers.LevelInfoHandler;
import com.lazermann.httpserver.handlers.SetInfoHandler;
import com.lazermann.httpserver.handlers.UserInfoHandler;
import com.lazermann.httpserver.storage.HazelcastStorage;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

import static com.lazermann.httpserver.Constants.URI_LEVELINFO;
import static com.lazermann.httpserver.Constants.URI_SETINFO;
import static com.lazermann.httpserver.Constants.URI_USERINFO;

public class MyHttpServer
{
    public static void main(String[] args) throws Exception
    {
        new MyHttpServer().start();
    }

    public void start() throws IOException
    {
        HazelcastStorage.init();
        HazelcastStorage.addTestData();

        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8765), 0);

        server.createContext(URI_USERINFO, new UserInfoHandler());
        server.createContext(URI_LEVELINFO, new LevelInfoHandler());
        server.createContext(URI_SETINFO, new SetInfoHandler());


        server.setExecutor(null);
        server.start();
    }
}
