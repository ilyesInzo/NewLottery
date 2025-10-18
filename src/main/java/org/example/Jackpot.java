package org.example;

import java.util.List;

public class Jackpot {
    List<Integer> winningChain;
    List<Integer> winningStarChain;

    @Override
    public String toString() {
        return "Jackpot{" +
                "winningChain=" + winningChain +
                ", winningStarChain=" + winningStarChain +
                '}';
    }
}
