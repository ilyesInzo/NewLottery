package org.example.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Historic;
import org.example.model.MagayoLottery;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MagayoLotteryApiService {

    public Historic fetchHistoricByDate(String url, List<Historic> histories, LocalDate date) {
        if (false) {
            fetchAsync(url, date);
            return null;
        }

        boolean exist = histories.stream().anyMatch(historic -> historic.getDate().equals(date));
        if (exist) {
            return null;
        }
        try {
            System.out.println("Sending Request To URL: " + url);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url.formatted(date)))
                    .GET()
                    .build();

            HttpResponse<String> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
            MagayoLottery result = mapper.readValue(response.body(), MagayoLottery.class);

            if ("0".equals(result.getError())) {
                return new Historic(result.getDate(), result.getWinningNumbers());
            } else {
                System.out.println("Got Error " + result.getError());
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Got Error while requesting Magayo Lottery" + e.getMessage());
        }
        return null;
    }

    private void fetchAsync(String url, LocalDate date) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(url.formatted(date)))
                    .GET()
                    .build();

            CompletableFuture<HttpResponse<String>> response = HttpClient.newBuilder()
                    .build()
                    .sendAsync(request, HttpResponse.BodyHandlers.ofString());

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Got Error while requesting Magayo Lottery" + e.getMessage());
        }
    }
}
