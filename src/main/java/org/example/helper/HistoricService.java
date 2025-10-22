package org.example.helper;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.Historic;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.example.helper.DateHelper.getNumberOfRemainingDayWeekInMonth;

public class HistoricService {

    private final MagayoLotteryApiService magayoLotteryApiService = new MagayoLotteryApiService();

    public void loadHistories(String filePath, String url, Map.Entry<DayOfWeek, DayOfWeek> dayOfWeeks) {
        List<Historic> histories = readHistories(filePath);
        List<Historic> newHistories = getNewHistories(url, histories, dayOfWeeks);
        writeHistories(filePath, newHistories);
    }

    private List<Historic> getNewHistories(String url, List<Historic> histories, Map.Entry<DayOfWeek, DayOfWeek> dayOfWeeks) {
        LocalDate today = LocalDate.now();
        int maxDays = 0;
        DayOfWeek dayOfWeek1 = dayOfWeeks.getKey();
        DayOfWeek dayOfWeek2 = dayOfWeeks.getValue();
        // maximum same two day of the week can be 9
        int maxHistoryToRetrieve =
                10 -
                        (getNumberOfRemainingDayWeekInMonth(dayOfWeek1, today.getMonth()) +
                                getNumberOfRemainingDayWeekInMonth(dayOfWeek2, today.getMonth()));

        LocalDate lastFriday = today.with(TemporalAdjusters.previous(dayOfWeek1));
        LocalDate lastWednesday = today.with(TemporalAdjusters.previous(dayOfWeek2));
        LocalDate lastDraw, beforeLastDraw;

        if (lastFriday.isAfter(lastWednesday)) {
            lastDraw = lastFriday;
            beforeLastDraw = lastWednesday;
        } else {
            lastDraw = lastWednesday;
            beforeLastDraw = lastFriday;
        }
        List<Historic> newHistories = new ArrayList<>();
        // we are allowed to send max 10 request every month
        // it is enough to get maximum the last 6 draw (3 weeks)

        while (maxDays < maxHistoryToRetrieve) {
            Historic newHistory;
            newHistory = magayoLotteryApiService.fetchHistoricByDate(url, histories, lastDraw);
            if (newHistory == null) {
                break;
            }
            newHistories.add(newHistory);
            newHistory = magayoLotteryApiService.fetchHistoricByDate(url, histories, beforeLastDraw);
            maxDays++;
            if (newHistory == null || maxDays >= maxHistoryToRetrieve) {
                break;
            }
            newHistories.add(newHistory);
            maxDays++;
            lastDraw = lastDraw.minusWeeks(1);
            beforeLastDraw = beforeLastDraw.minusWeeks(1);
        }
        histories.addAll(newHistories);
        Collections.sort(histories);
        System.out.println("Adding " + newHistories.size() + " new histories");
        return histories;
    }

    private List<Historic> readHistories(String filePath) {

        File file = new File(filePath);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        List<Historic> result = null;
        try {
            result = mapper.readValue(file, new TypeReference<List<Historic>>() {
            });
            Collections.sort(result);
            return result;
        } catch (IOException e) {
            System.out.println("Error Reading From History File");
        }

        return List.of();
    }

    private void writeHistories(String filePath, List<Historic> result) {
        File file = new File(
                filePath);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        Collections.sort(result);
        try {
            mapper.writeValue(file, result);
        } catch (IOException e) {
            System.out.println("Error Writing To History File");
        }
    }
}
