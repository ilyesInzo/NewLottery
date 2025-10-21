package org.example.dto;

public class GermanLottoLottery extends Lottery {

    private final static String filePath = "src/histories_6AUS49.json";

    public GermanLottoLottery() {
        super(filePath);
    }
}
