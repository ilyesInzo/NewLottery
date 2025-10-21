package org.example.dto;

public class EuroJackpotLottery extends Lottery {

    private final static String filePath = "src/histories_EuroJackpot.json";

    public EuroJackpotLottery() {
        super(filePath);
    }
}
