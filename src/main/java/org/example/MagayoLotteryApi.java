package org.example;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MagayoLotteryApi {
    private String error;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonAlias("draw")
    private LocalDate date;
    @JsonAlias("results")
    private String winningNumbers;

    public MagayoLotteryApi() {
    }

    public MagayoLotteryApi(String error, LocalDate date, String winningNumbers) {
        this.error = error;
        this.date = date;
        this.winningNumbers = winningNumbers;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getWinningNumbers() {
        return winningNumbers;
    }

    public void setWinningNumbers(String winningNumbers) {
        this.winningNumbers = winningNumbers;
    }
}