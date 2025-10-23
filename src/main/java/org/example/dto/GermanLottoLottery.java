package org.example.dto;

import org.example.model.Jackpot;

import java.time.DayOfWeek;
import java.util.*;

public class GermanLottoLottery extends Lottery {

    private final static String filePath = "src/histories_6AUS49.json";
    private final static String url = "https://www.magayo.com/api/results.php?api_key=5GkNYH4HYQ4AvbEjpf&game=de_lotto&draw=%s";
    private final static List<List<Integer>> listExcludeLotteryNumber = List.of(
            List.of(1, 2, 48, 49));
    private final static List<Integer> listExcludeLotteryStar = List.of(0, 9);
    private final static int generatedWinningNumber = 6;
    private final static int generatedWinningStar = 1;

    public GermanLottoLottery(List<Integer> winningNumber, List<Integer> winningStar, int nbExcludeWinningNumber) {
        super(filePath, url, listExcludeLotteryNumber, listExcludeLotteryStar, myLotteryHistory, winningNumber, winningStar, nbExcludeWinningNumber, generatedWinningNumber, generatedWinningStar);
    }

    @Override
    protected Jackpot getSpinJackpotResult(boolean isFoundStar, List<Integer> listExcludeLotteryStar, List<List<Integer>> listExcludeLotteryNumber) {
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
        GetResult(isFoundStar, listExcludeLotteryStar, rand, baseList, baseStartList, result, numberOfElements, listExcludeLotteryNumber);
        int randomIndex = rand.nextInt(baseStartList.size());
        Integer chosenValue = baseStartList.remove(randomIndex);
        resultStart.add(chosenValue);
        Jackpot jackpot = new Jackpot();
        jackpot.setWinningChain(result);
        jackpot.setWinningStarChain(resultStart);

        return jackpot;
    }

    @Override
    protected Map.Entry<DayOfWeek, DayOfWeek> getLotteryDays() {
        return new AbstractMap.SimpleEntry<>(DayOfWeek.SATURDAY, DayOfWeek.WEDNESDAY);
    }

    private final static List<List<Integer>> myLotteryHistory = List.of(
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
}
