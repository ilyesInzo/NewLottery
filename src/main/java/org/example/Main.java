package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        Calculate ca = new Calculate();
        try {
            ca.execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}