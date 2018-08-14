package com.lazermann.httpserver;


import com.lazermann.httpserver.handlers.LevelInfoHandler;
import com.lazermann.httpserver.handlers.SetInfoHandler;
import com.lazermann.httpserver.handlers.UserInfoHandler;
import com.lazermann.httpserver.storage.HazelcastStorage;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class MyHttpServer
{
    public static void main(String[] args) throws Exception {

        new MyHttpServer().start();

    }

    public void start() throws IOException
    {
        HazelcastStorage.init();
        HazelcastStorage.addTestData();

        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8765), 0);

        server.createContext("/userinfo/", new UserInfoHandler());
        server.createContext("/levelinfo/", new LevelInfoHandler());
        server.createContext("/setinfo/", new SetInfoHandler());


        server.setExecutor(null);// todo how it works??
        server.start();
    }
}
