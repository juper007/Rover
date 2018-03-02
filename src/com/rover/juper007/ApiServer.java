package com.rover.juper007;

import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ApiServer {
    public static Map<String, String> queryToMap(String query){
        Map<String, String> result = new HashMap<String, String>();
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            }else{
                result.put(pair[0], "");
            }
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/sitters", new SitterHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class SitterHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            Gson gson = new Gson();
            DB_Manager dbm = new DB_Manager();
            ArrayList<Sitter> sitters = dbm.getSitter();

            String response;
            Map<String, String> queryParams = queryToMap(t.getRequestURI().getQuery());
            if (queryParams.containsKey("callback")) {
                response = queryParams.get("callback") + "(" + gson.toJson(sitters) + ");";
            } else {
                response = gson.toJson(sitters);
            }

            Headers headers = t.getResponseHeaders();
            headers.add("Content-Type", "application/json");
            t.sendResponseHeaders(200, response.length());

            System.out.println(response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
