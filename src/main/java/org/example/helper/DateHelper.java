package org.example.helper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;

public class DateHelper {
    public static int getNumberOfRemainingDayWeekInMonth(DayOfWeek dayOfWeek, Month month) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.with(TemporalAdjusters.nextOrSame(dayOfWeek));
        int count = 0;
        while (startDate.getMonth().equals(month)) {
            count++;
            startDate = startDate.plusWeeks(1);
        }

        return count;
    }
}
