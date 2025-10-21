package org.example.factory;

import org.example.config.LotteryConfig;
import org.example.dto.GermanLottoLottery;
import org.example.dto.Lottery;

import javax.naming.ConfigurationException;

public class GermanLottoLotteryFactory extends LotteryFactory {
    @Override
    protected Lottery createLottery(LotteryConfig config) throws Exception {
        this.validateLotteryConfig(config);
        return new GermanLottoLottery();
    }

    @Override
    protected void validateLotteryConfig(LotteryConfig config) throws ConfigurationException {
        if (config.getWinningNumberFound() < 0 || config.getWinningNumberFound() > 6) {
            throw new ConfigurationException("Winning number should be between %s found %s".formatted(0, 6));
        }
    }
}
