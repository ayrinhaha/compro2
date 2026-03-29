package com.ayrin.service;

import java.util.ArrayList;
import java.util.Random;

public class HangmanService {
    private String word;
    private String hidden;
    private int maxIncorrect;
    private int wrongGuesses = 0;
    private int score = 0;
    private int rewardPoints;
    private ArrayList<Character> guessed = new ArrayList<>(); 
    private boolean isGameOver = false;
    private boolean isWin = false;

    public HangmanService(int difficulty) {
        setupGame(difficulty);
    }

    private void setupGame(int difficulty) {
        ArrayList<String> bank = new ArrayList<>();

        if (difficulty == 1) { //ez
            bank.add("flex");
            bank.add("duke");
            bank.add("tail");
            bank.add("worm");
            bank.add("last");
            bank.add("even");
            bank.add("make");
            bank.add("flat");
            bank.add("rise");
            bank.add("gate");
            bank.add("blue");
            bank.add("save");
            maxIncorrect = 7;
            rewardPoints = 5;
        } else if (difficulty == 2) { // medium
            bank.add("slump");
            bank.add("youth");
            bank.add("pound");
            bank.add("cater");
            bank.add("tooth");
            bank.add("slave");
            bank.add("lease");
            bank.add("plane");
            bank.add("alive");
            bank.add("glide");
            bank.add("doubt");
            bank.add("mercy");
            maxIncorrect = 10;
            rewardPoints = 7;
        } else { // hard
            bank.add("pattern");
            bank.add("athlete");
            bank.add("embrace");
            bank.add("century");
            bank.add("alcohol");
            bank.add("squeeze");
            bank.add("mixture");
            bank.add("stadium");
            bank.add("sausage");
            bank.add("strange");
            bank.add("ceiling");
            bank.add("reflect");
            maxIncorrect = 13;
            rewardPoints = 10;
        }

        // random pick
        Random rand = new Random();
        word = bank.get(rand.nextInt(bank.size()));

        // for hidin the word (****)
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            sb.append("*");
        }
        hidden = sb.toString();
    }

    public String processGuess(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "Please enter a letter.";
        }

        char g = Character.toLowerCase(input.trim().charAt(0));

        if (!Character.isLetter(g)) {
            return "Invalid input. Enter letters only.";
        }

        // check if already guessed 
        if (guessed.contains(g)) {
            return g + " was already guessed!";
        }

        guessed.add(g);

        if (word.indexOf(g) >= 0) {
            updateHidden(g);

            if (hidden.equals(word)) {
                isGameOver = true;
                isWin = true;
                this.score = rewardPoints; //  full points for winning
                return "Correct! You've won the game!";
            }
            return "Correct!";
        } else {
            wrongGuesses++;
            if (wrongGuesses >= maxIncorrect) {
                isGameOver = true;
                this.score = 0; // 0 points if the game is lost
                return "Wrong! No tries left.";
            }
            return "Wrong! Tries remaining: " + (maxIncorrect - wrongGuesses);
        }
    }

    private void updateHidden(char g) {
        char[] hiddenChars = hidden.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == g) {
                hiddenChars[i] = g;
            }
        }
        hidden = new String(hiddenChars);
    }

    
    public String getGameState() {
        return "Word: " + hidden + " | Mistakes: " + wrongGuesses + "/" + maxIncorrect;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isWin() {
        return isWin;
    }

    public int getScore() {
        return score;
    }

    public String getWord() {
        return word;
    }

    
}