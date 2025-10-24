package org.example.factory;

import org.example.config.LotteryConfig;
import org.example.dto.Lottery;

public abstract class LotteryFactory {
    public void executeLottery(LotteryConfig config) throws Exception {
        Lottery lottery = createLottery(config);
        if (config.isGenerateNumber()) {
            lottery.generateLottery(config.getNbWinningLottery(), config.isFindStars());
        }

        if (config.isShowProbability()) {
            lottery.showWinningProbability(config.getNbWinningLottery(), config.getWinningNumberFound(), config.isFindStars(), config.getWinningNumberEnum());
        }

        if (config.isCheckMyHistory()) {
            lottery.checkMyHistory(config.getWinningNumberEnum());
        }
    }

    protected abstract Lottery createLottery(LotteryConfig config) throws Exception;

    protected abstract void validateLotteryConfig(LotteryConfig config) throws Exception;

}
