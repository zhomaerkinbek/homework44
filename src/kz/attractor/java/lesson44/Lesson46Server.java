package kz.attractor.java.lesson44;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class Lesson46Server extends Lesson45Server{
    public Lesson46Server(String host, int port) throws IOException {
        super(host, port);
        registerGet("/cookie", this::cookieHandler);
    }

    private void cookieHandler(HttpExchange exchange) {

    }


}
