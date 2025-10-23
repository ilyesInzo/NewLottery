package org.example.factory;

import org.apache.commons.collections4.CollectionUtils;
import org.example.config.LotteryConfig;
import org.example.dto.GermanLottoLottery;
import org.example.dto.Lottery;

import javax.naming.ConfigurationException;

public class GermanLottoLotteryFactory extends LotteryFactory {
    @Override
    protected Lottery createLottery(LotteryConfig config) throws Exception {
        this.validateLotteryConfig(config);
        return new GermanLottoLottery(config.getWinningNumber(), config.getWinningStar(), config.getNbExcludeWinningNumber());
    }

    @Override
    protected void validateLotteryConfig(LotteryConfig config) throws ConfigurationException {
        if (config.getWinningNumberFound() < 0 || config.getWinningNumberFound() > 6) {
            throw new ConfigurationException("Winning number should be between %s found %s".formatted(0, 6));
        }
        if (CollectionUtils.size(config.getWinningNumber()) != 6) {
            throw new ConfigurationException("Winning number should have exactly %s number".formatted(6));
        }
        if (CollectionUtils.size(config.getWinningStar()) != 1) {
            throw new ConfigurationException("Winning star should have exactly %s number".formatted(1));
        }
    }
}
