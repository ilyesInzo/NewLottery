package org.example;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

public class Calculate {
    private final String TOKEN_EURO_JACKPOT = "https://www.magayo.com/api/results.php?api_key=mCVwFtv9c6DSDL2ESb&game=eurojackpot&draw=%s";
    private final String TOKEN_6_AUS_49 = "https://www.magayo.com/api/results.php?api_key=mCVwFtv9c6DSDL2ESb&game=de_lotto&draw=%s";
    private final String FilePath6Aus49 = "src/histories_6AUS49.json";
    private final String FilePathEuroJackpot = "src/histories_EuroJackpot.json";

    public void execute() throws IOException {
        int nbWinningLottery = 100;
        boolean isFoundStar = true;
        int winningNumberFound = 5;
        Lottery lottery = Lottery.EURO_JACKPOT;
        boolean showPropa = false;
        boolean checkHistory = true;
        int generatedWinningStar = lottery.equals(Lottery.EURO_JACKPOT) ? 2 : 1;
        int generatedWinningNumber = lottery.equals(Lottery.EURO_JACKPOT) ? 5 : 6;
        String filePath = lottery.equals(Lottery.EURO_JACKPOT) ? FilePathEuroJackpot : FilePath6Aus49;

        List<List<Integer>> listEuroLottery = List.of(
                List.of(8, 12, 13, 49, 50),
                List.of(4, 5, 24, 31, 41),
                List.of(10, 22, 38, 42, 48),
                List.of(1, 2, 49, 50),
                List.of(24, 25, 19, 30, 31, 32, 36));
        List<Integer> listEuroStar = List.of(2, 4);
        List<Integer> winningEuroNumber = List.of(18, 21, 34, 35, 46);
        List<Integer> winningEuroStar = List.of(3, 10);

        List<List<Integer>> list6AUS49Lottery = List.of(
                List.of(9, 12, 14, 32, 37, 41),
                List.of(6, 11, 16, 27, 29, 33),
                List.of(6, 13, 15, 19, 43, 44),
                List.of(1, 2, 48, 49));
        List<Integer> list6AUS49Star = List.of(0, 9, 8);
        List<Integer> winning6AUS49Number = List.of(11, 12, 13, 20, 27, 43);
        List<Integer> winning6AUS49Star = List.of(5);

        List<List<Integer>> listLottery = lottery == Lottery.GERMAN_6AUS49 ? list6AUS49Lottery : listEuroLottery;
        List<Integer> listStar = lottery == Lottery.GERMAN_6AUS49 ? list6AUS49Star : listEuroStar;
        List<Integer> winningNumber = lottery == Lottery.GERMAN_6AUS49 ? winning6AUS49Number : winningEuroNumber;
        List<Integer> winningStar = lottery == Lottery.GERMAN_6AUS49 ? winning6AUS49Star : winningEuroStar;

        List<Integer> allPossibleWinningNumbers = new ArrayList<>();
        List<Integer> allPossibleWinningStarNumbers = new ArrayList<>();

        List<Historic> historics = readHistories(filePath);
        /*historics.add(new Historic(LocalDate.now(), "0,1,2"));
        historics.add(new Historic(LocalDate.now().minusWeeks(1), "0,1,2"));
        historics.add(new Historic(LocalDate.now().plusWeeks(1), "0,1,2"));*/
        historics = getEuroJackpotNewHistory(historics);

        writeHistories(filePath, historics);

        for (int i = 0; i < nbWinningLottery; i++) {
            Jackpot jackpot = getSpinJackpotResult(lottery, isFoundStar, listStar, listLottery);
            allPossibleWinningNumbers.addAll(jackpot.winningChain);
            allPossibleWinningStarNumbers.addAll(jackpot.winningStarChain);
            System.out.println(jackpot);
        }
        System.out.println("Winning Numbers Occ");
        displayWinningBestOccurrence(allPossibleWinningNumbers, generatedWinningNumber);
        System.out.println("Winning Stars Occ");
        displayWinningBestOccurrence(allPossibleWinningStarNumbers, generatedWinningStar);

        if (showPropa) {
            double tryCount = 0;
            for (int i = 0; i < nbWinningLottery; i++) {
                tryCount = tryCount
                        + ((double) 1 / whenToWin(winningNumber, winningStar, winningNumberFound, isFoundStar, lottery,
                        listStar, listLottery));
            }
            System.out.println(tryCount / nbWinningLottery);
        }

        if (checkHistory) {

            checkHistory(lottery, winningNumber);

        }
    }

    private Jackpot displayWinnerEuro(boolean isFoundStar, List<Integer> lastStartList, List<List<Integer>> lastList) {
        Random rand = new Random();
        List<Integer> baseList = new ArrayList<>();
        List<Integer> baseStartList = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12));
        for (int i = 1; i <= 50; i++) {
            baseList.add(i);
        }
        List<Integer> result = new ArrayList<>();
        List<Integer> resultStart = new ArrayList<>();

        int numberOfElements = 5;

        // remove all already values
        GetResult(isFoundStar, lastStartList, rand, baseList, baseStartList, result, numberOfElements, lastList);
        int randomIndex = rand.nextInt(baseStartList.size());
        Integer chosenValue = baseStartList.remove(randomIndex);
        resultStart.add(chosenValue);
        randomIndex = rand.nextInt(baseStartList.size());
        chosenValue = baseStartList.remove(randomIndex);
        resultStart.add(chosenValue);

        Jackpot jackpot = new Jackpot();
        jackpot.winningChain = result;
        jackpot.winningStarChain = resultStart;
        return jackpot;
    }

    Jackpot displayWinner6Aus49(boolean isFoundStar, List<Integer> lastStartList, List<List<Integer>> lastList) {
        Random rand = new Random();

        List<Integer> baseList = new ArrayList<>();
        List<Integer> baseStartList = new ArrayList<>(List.of(0, 2, 3, 4, 5, 6, 7, 8, 9));
        for (int i = 1; i <= 49; i++) {
            baseList.add(i);
        }
        List<Integer> result = new ArrayList<>();
        List<Integer> resultStart = new ArrayList<>();

        int numberOfElements = 6;

        // remove all already values
        GetResult(isFoundStar, lastStartList, rand, baseList, baseStartList, result, numberOfElements, lastList);
        int randomIndex = rand.nextInt(baseStartList.size());
        Integer chosenValue = baseStartList.remove(randomIndex);
        resultStart.add(chosenValue);
        Jackpot jackpot = new Jackpot();
        jackpot.winningChain = result;
        jackpot.winningStarChain = resultStart;
        return jackpot;
    }

    private void GetResult(boolean isFoundStar, List<Integer> lastStartList, Random rand, List<Integer> baseList,
                           List<Integer> baseStartList, List<Integer> result, int numberOfElements, List<List<Integer>> lastList) {
        lastList.forEach(integers -> baseList.removeIf(integers::contains));
        if (isFoundStar) {
            baseStartList.removeIf(lastStartList::contains);
        }

        for (int i = 0; i < numberOfElements; i++) {
            int randomIndex = rand.nextInt(baseList.size());
            Integer chosenValue = baseList.remove(randomIndex);
            result.add(chosenValue);
        }
        result.sort(Integer::compareTo);
    }

    // if isFoundStar = true the lastStartList will not be taken into consideration
    // and not removed from waiting list
    private int whenToWin(List<Integer> winningChain, List<Integer> winningStarChain, int numberFound,
                          boolean isFoundStar, Lottery lottery, List<Integer> lastStartList, List<List<Integer>> lastList) {
        int tryCount = 0;
        List<Integer> tries;
        boolean starFound;
        Jackpot jackpot;
        do {
            jackpot = getSpinJackpotResult(lottery, isFoundStar, lastStartList, lastList);
            List<Integer> result = jackpot.winningChain;
            List<Integer> resulStart = jackpot.winningStarChain;
            tries = result.stream().filter(winningChain::contains).collect(Collectors.toList());
            starFound = !isFoundStar || resulStart.stream().filter(winningStarChain::contains)
                    .collect(Collectors.toSet()).size() == winningStarChain.size();
            tryCount++;

        } while (tries.size() < numberFound || !starFound);
        tries.sort(Integer::compareTo);
        System.out.println(jackpot + " found after " + tryCount + " tries, with common values : " + tries);
        return tryCount;
    }

    private Jackpot getSpinJackpotResult(Lottery lottery, boolean isFoundStar, List<Integer> lastStartList,
                                         List<List<Integer>> lastList) {
        switch (lottery) {
            case EURO_JACKPOT -> {
                return displayWinnerEuro(isFoundStar, lastStartList, lastList);
            }
            case GERMAN_6AUS49 -> {
                return displayWinner6Aus49(isFoundStar, lastStartList, lastList);
            }
        }
        throw new IllegalStateException("Not Implemented Lottery");
    }

    private void displayWinningBestOccurrence(List<Integer> allPossibleWinningNumbers, int limit) {

        Map<Integer, Integer> map = new HashMap<>();
        Set<Integer> winningSet = new HashSet<>(allPossibleWinningNumbers);
        for (Integer winningNumber : winningSet)
            map.put(winningNumber, Collections.frequency(allPossibleWinningNumbers, winningNumber));

        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue((integer, t1) -> t1 - integer))
                .limit(limit)
                .forEach(System.out::println);
    }

    private void checkHistory(Lottery lottery, List<Integer> winningNumber) {
        List<List<Integer>> histories = lottery == Lottery.GERMAN_6AUS49 ? histories6AUS49Lottery
                : historiesEuroJackpotLottery;

        Map<Long, List<List<Integer>>> map = histories.stream()
                .collect(Collectors
                        .groupingBy(item -> item.stream().filter(winningNumber::contains).count(),
                                Collectors.mapping(integers -> integers, Collectors.toList())));
        Map.Entry<Long, List<List<Integer>>> entry = map.entrySet().stream()
                .max((entry1, entry2) -> Math.toIntExact((entry1.getKey() - entry2.getKey()))).get();
        System.out.println("Max numbers found '" + entry.getKey() + "' in this histories \n" + entry.getValue());

    }

    private List<Historic> readHistories(String filePath) throws IOException {
        File file = new File(
                filePath);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        List<Historic> result = mapper.readValue(file, new TypeReference<List<Historic>>() {
        });
        Collections.sort(result);
        return result;
    }

    private void writeHistories(String filePath, List<Historic> result) throws IOException {
        File file = new File(
                filePath);
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        Collections.sort(result);
        mapper.writeValue(file, result);
    }

    private List<Historic> getEuroJackpotNewHistory(List<Historic> histories) {
        LocalDate today = LocalDate.now();
        int maxDays = 0;
        // maximum same two day of the week can be 9
        int maxHistoryToRetrieve =
                10 -
                        (getNumberOfRemainingDayWeekInMonth(DayOfWeek.FRIDAY, today.getMonth()) +
                                getNumberOfRemainingDayWeekInMonth(DayOfWeek.WEDNESDAY, today.getMonth()));

        LocalDate lastFriday = today.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
        LocalDate lastWednesday = today.with(TemporalAdjusters.previous(DayOfWeek.WEDNESDAY));
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
            newHistory = fetchHistoricByDate(histories, lastDraw);
            if (newHistory == null) {
                break;
            }
            newHistories.add(newHistory);
            newHistory = fetchHistoricByDate(histories, beforeLastDraw);
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
        System.out.println("Adding " + histories.size() + " histories");
        return histories;
    }

    private Historic fetchHistoricByDate(List<Historic> histories, LocalDate date) {
        boolean exist = histories.stream().anyMatch(historic -> historic.getDate().equals(date));
        if (exist) {
            return null;
        }
        // TODO call api
        return new Historic(date, "");
    }

    private int getNumberOfRemainingDayWeekInMonth(DayOfWeek dayOfWeek, Month month) {
        LocalDate today = LocalDate.now();
        LocalDate startDate = today.with(TemporalAdjusters.nextOrSame(dayOfWeek));
        int count = 0;
        while (startDate.getMonth().equals(month)) {
            count++;
            startDate = startDate.plusWeeks(1);
        }

        return count;
    }

    static final List<List<Integer>> histories6AUS49Lottery = List.of(
            List.of(12, 22, 24, 31, 35, 40), // 7 27.09.2025
            List.of(6, 11, 16, 27, 29, 32), // 5 24.09.2025
            List.of(11, 13, 18, 28, 39, 40), // 4 20.09.2025
            List.of(5, 11, 18, 27, 39, 40), // 4 17.09.2025
            List.of(11, 12, 13, 20, 27, 43), // 5 13.09.2025
            List.of(23, 27, 37, 40, 45, 48), // 8 23.08.2025
            List.of(9, 32, 35, 38, 39, 46), // 4 30.07.2025
            List.of(5, 11, 14, 39, 43, 46), // 4 30.07.2025
            List.of(5, 11, 14, 39, 43, 46), // 4 30.07.2025
            List.of(4, 22, 26, 29, 45, 46), // 5 03.05.2025
            List.of(5, 19, 31, 37, 40, 47), // 6, 23.04.2025
            List.of(18, 22, 25, 31, 45, 45));// 9 15.02.2025

    static final List<List<Integer>> historiesEuroJackpotLottery = List.of(
            List.of(7, 15, 26, 29, 35), // 4,6 14.10.2025
            List.of(6, 15, 26, 29, 35), // 4,6 10.10.2025
            List.of(10, 34, 42, 45, 47), // 4,6 03.10.2025
            List.of(10, 28, 34, 36, 47), // 3,10 16.09.2025 and 23.09.2025
            List.of(16, 19, 30, 32, 39), // 3,10 12.09.2025
            List.of(4, 8, 33, 37, 43), // 4,12 9.09.2025
            List.of(7, 9, 17, 20, 23), // 4,12 5.09.2025
            List.of(9, 11, 12, 38, 39), // 10,12 30.08.2025
            List.of(10, 12, 30, 29, 30), // 1,6 29.08.2025
            List.of(24, 25, 30, 39, 48), // 3,12 26.08.2025
            List.of(6, 10, 22, 34, 45), // 1,10 22.08.2025
            List.of(12, 14, 27, 39, 47), // 1,5 19.08.2025
            List.of(8, 24, 31, 39, 41), // 9,10 22.04.2025
            List.of(7, 17, 27, 29, 34));// 4,12 14.03.2025


}
