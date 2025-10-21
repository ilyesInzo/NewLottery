package org.example.config;

public class LotteryConfig {
    private boolean showProbability = false;
    private boolean checkMyHistory = false;
    private boolean checkLotteryHistory = false;
    private boolean generateNumber = false;

    private int nbWinningLottery = 100;
    private int winningNumberFound = 5;
    private boolean findStars = false;

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

}
