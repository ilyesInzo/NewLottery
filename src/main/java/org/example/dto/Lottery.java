package org.example.dto;

import org.example.helper.HistoricService;
import org.example.helper.MagayoLotteryApiService;
import org.example.model.Jackpot;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Lottery {

    private String filePath;
    private String url;

    private List<List<Integer>> listExcludeLotteryNumber;
    private List<Integer> listExcludeLotteryStar;
    private List<Integer> winningNumber;
    private List<Integer> winningStar;
    private List<List<Integer>> myLotteryHistory;
    private int generatedWinningNumber;
    private int generatedWinningStar;

    private final HistoricService historicService;

    public Lottery(String filePath,
                   String url,
                   List<List<Integer>> listExcludeLotteryNumber,
                   List<Integer> listExcludeLotteryStar,
                   List<List<Integer>> myLotteryHistory,
                   List<Integer> winningNumber,
                   List<Integer> winningStar,
                   int generatedWinningNumber,
                   int generatedWinningStar) {
        this.filePath = filePath;
        this.url = url;
        this.listExcludeLotteryNumber = listExcludeLotteryNumber;
        this.listExcludeLotteryStar = listExcludeLotteryStar;
        this.winningNumber = winningNumber;
        this.winningStar = winningStar;
        this.myLotteryHistory = myLotteryHistory;
        this.generatedWinningNumber = generatedWinningNumber;
        this.generatedWinningStar = generatedWinningStar;
        this.historicService = new HistoricService();
        this.historicService.loadHistories(this.filePath, this.url, getLotteryDays());
    }

    public void generateLottery(int nbWinningLottery, boolean isFoundStar) {
        List<Integer> allPossibleWinningNumbers = new ArrayList<>();
        List<Integer> allPossibleWinningStarNumbers = new ArrayList<>();
        for (int i = 0; i < nbWinningLottery; i++) {
            Jackpot jackpot = getSpinJackpotResult(isFoundStar, listExcludeLotteryStar, listExcludeLotteryNumber);
            allPossibleWinningNumbers.addAll(jackpot.getWinningChain());
            allPossibleWinningStarNumbers.addAll(jackpot.getWinningStarChain());
        }
        System.out.println("Winning Numbers Occ");
        displayWinningBestOccurrence(allPossibleWinningNumbers, generatedWinningNumber);
        System.out.println("Winning Stars Occ");
        displayWinningBestOccurrence(allPossibleWinningStarNumbers, generatedWinningStar);
    }

    public void checkMyHistory() {
        Map<Long, List<List<Integer>>> map = myLotteryHistory.stream()
                .collect(Collectors
                        .groupingBy(item -> item.stream().filter(winningNumber::contains).count(),
                                Collectors.mapping(integers -> integers, Collectors.toList())));
        Map.Entry<Long, List<List<Integer>>> entry = map.entrySet().stream()
                .max((entry1, entry2) -> Math.toIntExact((entry1.getKey() - entry2.getKey()))).get();
        System.out.println("For " + winningNumber + " Max numbers found '" + entry.getKey() + "' in my histories \n" + entry.getValue());
    }

    public void showWinningProbability(int nbWinningLottery, int winningNumberFound, boolean isFoundStar) {
        double tryCount = 0;
        for (int i = 0; i < nbWinningLottery; i++) {
            tryCount = tryCount
                    + ((double) 1 / whenToWin(winningNumber, winningStar, winningNumberFound, isFoundStar,
                    listExcludeLotteryStar, listExcludeLotteryNumber));
        }
        System.out.println(tryCount / nbWinningLottery);
    }

    protected void GetResult(boolean isFoundStar, List<Integer> lastStartList, Random rand, List<Integer> baseList,
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

    protected abstract Jackpot getSpinJackpotResult(boolean isFoundStar, List<Integer> listExcludeLotteryStar, List<List<Integer>> listExcludeLotteryNumber);

    protected abstract Map.Entry<DayOfWeek, DayOfWeek> getLotteryDays();

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

    private int whenToWin(List<Integer> winningChain, List<Integer> winningStarChain, int numberFound,
                          boolean isFoundStar, List<Integer> lastStartList, List<List<Integer>> lastList) {
        int tryCount = 0;
        List<Integer> tries;
        boolean starFound;
        Jackpot jackpot;
        do {
            jackpot = getSpinJackpotResult(isFoundStar, lastStartList, lastList);
            List<Integer> result = jackpot.getWinningChain();
            List<Integer> resulStart = jackpot.getWinningStarChain();
            tries = result.stream().filter(winningChain::contains).collect(Collectors.toList());
            starFound = !isFoundStar || resulStart.stream().filter(winningStarChain::contains)
                    .collect(Collectors.toSet()).size() == winningStarChain.size();
            tryCount++;

        } while (tries.size() < numberFound || !starFound);
        tries.sort(Integer::compareTo);
        System.out.println(jackpot + " found after " + tryCount + " tries, with common values : " + tries);
        return tryCount;
    }
}
