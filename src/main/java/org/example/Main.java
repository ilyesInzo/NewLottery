package org.example;

import org.example.config.LotteryConfig;
import org.example.factory.EuroJackpotLotteryFactory;
import org.example.factory.GermanLottoLotteryFactory;
import org.example.factory.LotteryFactory;

import java.io.IOException;
import java.util.List;

public class Main {
    private static Lottery lottery = Lottery.EURO_JACKPOT;


    public static void main(String[] args) {

        Calculate ca = new Calculate();
        try {
            //ca.execute();
            LotteryConfig config = getConfig();
            LotteryFactory lotteryFactory = switch (config.getLottery()) {
                case EURO_JACKPOT -> new EuroJackpotLotteryFactory();
                case GERMAN_6AUS49 -> new GermanLottoLotteryFactory();
            };
            lotteryFactory.executeLottery(getConfig());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static LotteryConfig getConfig() {
        LotteryConfig config = new LotteryConfig();
        config.setLottery(lottery);
        switch (config.getLottery()) {
            case EURO_JACKPOT -> {
                config.setWinningStar(List.of(3, 10));
                config.setWinningNumber(List.of(18, 21, 34, 35, 46));
            }
            case GERMAN_6AUS49 -> {
                config.setWinningStar(List.of(5));
                config.setWinningNumber(List.of(11, 12, 13, 20, 27, 43));
            }
        }
        ;
        return config;
    }
}