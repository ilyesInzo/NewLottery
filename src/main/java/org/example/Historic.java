package org.example;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Historic implements Comparable<Historic> {
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
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
        return "On %s %s with winning numbers %s".formatted(date.getDayOfWeek().toString(), date, winningNumbers);
    }

    @Override
    public int compareTo(Historic o) {
        return o.date.compareTo(this.date);
    }
}
