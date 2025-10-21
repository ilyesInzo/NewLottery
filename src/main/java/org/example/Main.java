package org.example;

import org.example.config.LotteryConfig;
import org.example.factory.EuroJackpotLotteryFactory;
import org.example.factory.GermanLottoLotteryFactory;
import org.example.factory.LotteryFactory;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        Calculate ca = new Calculate();
        try {
            ca.execute();

            /*LotteryFactory lotteryFactory = new EuroJackpotLotteryFactory();
            lotteryFactory.executeLottery(new LotteryConfig());*/

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}