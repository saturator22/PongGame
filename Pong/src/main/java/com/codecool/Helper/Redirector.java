package com.codecool.Helper;

import com.sun.net.httpserver.HttpExchange;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

public class Redirector {

    public static void redirect(HttpExchange exchange, String whereTo) throws IOException {
        String hostPort = exchange.getRequestHeaders().get("HOST").get(0);
        exchange.getResponseHeaders().set("Location", "http://" + hostPort + whereTo);
        exchange.sendResponseHeaders(301, -1);
    }

    public static Map<String,String> getPostStringData(HttpExchange exchange) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(inputStreamReader);
        String formData = br.readLine();
        return parseFormData(formData);
    }

    private static Map <String, String> parseFormData(String formData) throws UnsupportedEncodingException {
        Map <String, String> map = new HashMap<>();
        String[] pairs = formData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            String value = URLDecoder.decode(keyValue[1], "UTF-8");
            map.put(keyValue[0], value);
        }
        return map;
    }
}
