package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LatestDataRequestSender {
    private final String ACCESS_KEY = "lE8mR9f2UIbOhG6Tywhb4yyB7FmPExT1";
    private final ObjectMapper objectMapper = new ObjectMapper();
    public LatestData getLatestData(String dajeWalute, String dostajeWalute) throws IOException, URISyntaxException, InterruptedException {
        String getUrl = "https://api.apilayer.com/exchangerates_data/latest?symbols=" + dostajeWalute + "&base=" + dajeWalute;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(getUrl))
                .header("apikey", ACCESS_KEY)
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newBuilder().build().send(request, HttpResponse.BodyHandlers.ofString());

        return objectMapper.readValue(response.body(), LatestData.class);
    }
}
