package org.example.dto;

public class Lottery {

    private String filePath;

    public Lottery(String filePath) {
        this.filePath = filePath;
    }

    public void getFilePath() {
        System.out.println("Lottery getFilePath " + filePath);
    }
}
