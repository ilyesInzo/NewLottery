package org.example.dto;

import org.example.model.Jackpot;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Lottery {

    private String filePath;
    private List<List<Integer>> listExcludeLotteryNumber;
    private List<Integer> listExcludeLotteryStar;
    private List<Integer> winningNumber;
    private List<Integer> winningStar;
    private List<List<Integer>> myLotteryHistory;
    private int generatedWinningNumber;
    private int generatedWinningStar;

    public Lottery(String filePath,
                   List<List<Integer>> listExcludeLotteryNumber,
                   List<Integer> listExcludeLotteryStar,
                   List<List<Integer>> myLotteryHistory,
                   List<Integer> winningNumber,
                   List<Integer> winningStar,
                   int generatedWinningNumber,
                   int generatedWinningStar) {
        this.filePath = filePath;
        this.listExcludeLotteryNumber = listExcludeLotteryNumber;
        this.listExcludeLotteryStar = listExcludeLotteryStar;
        this.winningNumber = winningNumber;
        this.winningStar = winningStar;
        this.myLotteryHistory = myLotteryHistory;
        this.generatedWinningNumber = generatedWinningNumber;
        this.generatedWinningStar = generatedWinningStar;
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
}
