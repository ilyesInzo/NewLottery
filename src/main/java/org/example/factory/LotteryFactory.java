package org.example.factory;

import org.example.WinningNumberEnum;
import org.example.config.LotteryConfig;
import org.example.dto.Lottery;

public abstract class LotteryFactory {
    public void executeLottery(LotteryConfig config) throws Exception {
        Lottery lottery = createLottery(config);
        if (config.isGenerateNumber() || WinningNumberEnum.GENERATED == config.getWinningNumberEnum()) {
            lottery.generateLottery(config.getNbWinningLottery(), config.isFindStars());
        }

        lottery.setEffectiveWinningNumber(config.getWinningNumberEnum());

        if (config.isShowProbability()) {
            lottery.showWinningProbability(config.getNbWinningLottery(), config.getWinningNumberFound(), config.isFindStars());
        }

        if (config.isCheckMyHistory()) {
            lottery.checkHistory(true);
        }

        if (config.isCheckLotteryHistory()) {
            lottery.checkHistory(false);
        }
    }

    protected abstract Lottery createLottery(LotteryConfig config) throws Exception;

    protected abstract void validateLotteryConfig(LotteryConfig config) throws Exception;

}
