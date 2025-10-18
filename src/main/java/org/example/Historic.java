package org.example;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Historic  {
    @JsonDeserialize(using=LocalDateDeserializer.class)
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    private LocalDate date;
    private String winningNumbers;

    public Historic() {
    }

    public Historic(LocalDate date, String winningNumbers) {
        this.date = date;
        this.winningNumbers = winningNumbers;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getWinningNumbers() {
        return winningNumbers;
    }

    @Override
    public String toString() {
        return "Historic{" +
                "date=" + date +
                ", winningNumbers='" + winningNumbers + '\'' +
                '}';
    }
}
