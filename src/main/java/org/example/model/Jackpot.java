package org.example.model;

import java.util.List;

public class Jackpot {
    private List<Integer> winningChain;
    private List<Integer> winningStarChain;

    @Override
    public String toString() {
        return "Jackpot{" +
                "winningChain=" + winningChain +
                ", winningStarChain=" + winningStarChain +
                '}';
    }

    public List<Integer> getWinningStarChain() {
        return winningStarChain;
    }

    public void setWinningStarChain(List<Integer> winningStarChain) {
        this.winningStarChain = winningStarChain;
    }

    public List<Integer> getWinningChain() {
        return winningChain;
    }

    public void setWinningChain(List<Integer> winningChain) {
        this.winningChain = winningChain;
    }
}
