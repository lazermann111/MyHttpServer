package server;


import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import handlers.UserInfoHandler;

import java.net.InetSocketAddress;

public class MyHttpServer
{
    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create();
        server.bind(new InetSocketAddress(8765), 0);

        HttpContext context = server.createContext("/userinfo/", new UserInfoHandler());
        HttpContext context2 = server.createContext("/levelinfo/", new UserInfoHandler());
        HttpContext context3 = server.createContext("/userinfo/", new UserInfoHandler());


        server.setExecutor(null);// todo how it works??
        server.start();
    }
}
