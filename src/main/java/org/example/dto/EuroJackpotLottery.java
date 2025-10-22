package org.example.dto;

import org.example.model.Jackpot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EuroJackpotLottery extends Lottery {

    private final static String filePath = "src/histories_EuroJackpot.json";
    private final static List<List<Integer>> listExcludeLotteryNumber = List.of(
            List.of(1, 2, 49, 50),
            List.of(24, 25, 19, 30, 31, 32, 36));
    private final static List<Integer> listExcludeLotteryStar = List.of(3, 10);
    private final static int generatedWinningNumber = 5;
    private final static int generatedWinningStar = 2;

    public EuroJackpotLottery(List<Integer> winningNumber, List<Integer> winningStar) {
        super(filePath, listExcludeLotteryNumber, listExcludeLotteryStar, myLotteryHistory, winningNumber, winningStar, generatedWinningNumber, generatedWinningStar);
    }

    @Override
    protected Jackpot getSpinJackpotResult(boolean isFoundStar, List<Integer> listExcludeLotteryStar, List<List<Integer>> listExcludeLotteryNumber) {
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
        GetResult(isFoundStar, listExcludeLotteryStar, rand, baseList, baseStartList, result, numberOfElements, listExcludeLotteryNumber);
        int randomIndex = rand.nextInt(baseStartList.size());
        Integer chosenValue = baseStartList.remove(randomIndex);
        resultStart.add(chosenValue);
        randomIndex = rand.nextInt(baseStartList.size());
        chosenValue = baseStartList.remove(randomIndex);
        resultStart.add(chosenValue);

        Jackpot jackpot = new Jackpot();
        jackpot.setWinningChain(result);
        jackpot.setWinningStarChain(resultStart);
        return jackpot;
    }

    private final static List<List<Integer>> myLotteryHistory = List.of(
            List.of(14, 18, 28, 33, 37), // 1,8 17.10.2025
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
