package com.example;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class Api {
    public static void writer(String cityName) throws IOException, InterruptedException {
        JsonNode jsonNode;
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://api.weatherstack.com/current?access_key=8d4bf25d0167b6938c9da6665f91ec06&query="+cityName))
                .build();

        HttpResponse<String> httpResponse ;
        httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        jsonNode = objectMapper.readTree(httpResponse.body());
        
        ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        writer.writeValue(new File("meteo-"+cityName+".json") , jsonNode );
    }

    public static JsonNode callApi(String cityName) throws IOException, InterruptedException  {
        JsonNode jsonNode;
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://api.weatherstack.com/current?access_key=8d4bf25d0167b6938c9da6665f91ec06&query="+cityName))
                .build();

        HttpResponse<String> httpResponse ;
        httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        ObjectMapper objectMapper = new ObjectMapper();
        // ObjectWriter writer = objectMapper.writer(new DefaultPrettyPrinter());
        jsonNode = objectMapper.readTree(httpResponse.body());
        // writer.writeValue(new File("meteo-"+cityName+".json") , jsonNode );
        return jsonNode;
    }
}
