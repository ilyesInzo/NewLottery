package org.example.config;

import org.example.Lottery;
import org.example.WinningNumberEnum;

import java.util.List;

public class LotteryConfig {
    private Lottery lottery;
    private WinningNumberEnum winningNumberEnum = WinningNumberEnum.GENERATED;

    private boolean showProbability = false;
    private boolean checkMyHistory = true;
    private boolean checkLotteryHistory = true;
    private boolean generateNumber = true;

    private int nbWinningLottery = 100;
    private int winningNumberFound = 5;
    private int nbExcludeWinningNumber = 5;
    private boolean findStars = false;

    private List<Integer> winningNumber;
    private List<Integer> winningStar;

    public int getWinningNumberFound() {
        return winningNumberFound;
    }

    public void setWinningNumberFound(int winningNumberFound) {
        this.winningNumberFound = winningNumberFound;
    }

    public boolean isShowProbability() {
        return showProbability;
    }

    public void setShowProbability(boolean showProbability) {
        this.showProbability = showProbability;
    }

    public boolean isCheckMyHistory() {
        return checkMyHistory;
    }

    public void setCheckMyHistory(boolean checkMyHistory) {
        this.checkMyHistory = checkMyHistory;
    }

    public boolean isCheckLotteryHistory() {
        return checkLotteryHistory;
    }

    public void setCheckLotteryHistory(boolean checkLotteryHistory) {
        this.checkLotteryHistory = checkLotteryHistory;
    }

    public boolean isGenerateNumber() {
        return generateNumber;
    }

    public void setGenerateNumber(boolean generateNumber) {
        this.generateNumber = generateNumber;
    }

    public int getNbWinningLottery() {
        return nbWinningLottery;
    }

    public void setNbWinningLottery(int nbWinningLottery) {
        this.nbWinningLottery = nbWinningLottery;
    }

    public boolean isFindStars() {
        return findStars;
    }

    public void setFindStars(boolean findStars) {
        this.findStars = findStars;
    }

    public List<Integer> getWinningNumber() {
        return winningNumber;
    }

    public void setWinningNumber(List<Integer> winningNumber) {
        this.winningNumber = winningNumber;
    }

    public List<Integer> getWinningStar() {
        return winningStar;
    }

    public void setWinningStar(List<Integer> winningStar) {
        this.winningStar = winningStar;
    }

    public Lottery getLottery() {
        return lottery;
    }

    public void setLottery(Lottery lottery) {
        this.lottery = lottery;
    }

    public int getNbExcludeWinningNumber() {
        return nbExcludeWinningNumber;
    }

    public void setNbExcludeWinningNumber(int nbExcludeWinningNumber) {
        this.nbExcludeWinningNumber = nbExcludeWinningNumber;
    }

    public WinningNumberEnum getWinningNumberEnum() {
        return winningNumberEnum;
    }

    public void setWinningNumberEnum(WinningNumberEnum winningNumberEnum) {
        this.winningNumberEnum = winningNumberEnum;
    }
}
