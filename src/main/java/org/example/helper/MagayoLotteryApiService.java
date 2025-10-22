package org.example.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.Lottery;
import org.example.model.Historic;
import org.example.model.MagayoLottery;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;

public class MagayoLotteryApiService {

    private Historic fetchHistoricByDate(String url, Lottery lottery, List<Historic> histories, LocalDate date) {
        boolean exist = histories.stream().anyMatch(historic -> historic.getDate().equals(date));
        if (exist) {
            return null;
        }
        try {
            // TODO replace with URL
            //(lottery.equals(Lottery.EURO_JACKPOT) ? TOKEN_EURO_JACKPOT : TOKEN_6_AUS_49)
            url = url.formatted(date);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://api.github.com/users/mojombo"))
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
                System.out.println("Got Error" + result.getError());
            }

        } catch (URISyntaxException | IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
