package com.ayrin.service;

import java.util.Random;
import java.io.*;

/**
 * Service class that handles the core logic of the Hangman game.
 *
 * This class manages word selection, player guesses, game state,
 * scoring, and win/loss conditions based on the selected difficulty.
 *
 * @author ayrinhaha
 */
public class HangmanService {
    private String word;
    private String hidden;
    private int maxIncorrect;
    private int wrongGuesses = 0;
    private int score = 0;
    private int rewardPoints;
    private char[] guessed = new char[26];
    private int guessedCount = 0;
    private boolean isGameOver = false;
    private boolean isWin = false;

    /**
     * Constructs a Hangman game with a selected difficulty.
     *
     * @param difficulty the difficulty level (1 = easy, 2 = medium, 3 = hard)
     */
    public HangmanService(int difficulty) {
        setupGame(difficulty);
    }

    /**
     * Initializes the game based on difficulty.
     *
     * Reads words from a text file depending on difficulty,
     * selects a random word, and prepares the hidden version.
     *
     * @param difficulty the selected difficulty level
     */
    private void setupGame(int difficulty) {
        String fileName;

        if (difficulty == 1) {
            fileName = "easy.txt";
            maxIncorrect = 7;
            rewardPoints = 5;
        } else if (difficulty == 2) {
            fileName = "medium.txt";
            maxIncorrect = 10;
            rewardPoints = 7;
        } else {
            fileName = "hard.txt";
            maxIncorrect = 13;
            rewardPoints = 10;
        }

        String[] words = loadWords(fileName);

        Random rand = new Random();
        word = words[rand.nextInt(words.length)];

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            sb.append("*");
        }
        hidden = sb.toString();
    }

    /**
     * Loads words from a text file into a String array.
     *
     * @param fileName the file containing words
     * @return an array of words
     */
    private String[] loadWords(String fileName) {
        int count = 0;

        // first pass: count lines
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while (br.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] words = new String[count];
        int index = 0;

        // second pass: store words
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                words[index++] = line.trim();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return words;
    }

    /**
     * Processes a player's guess.
     *
     * Validates input, checks if the letter was already guessed,
     * updates the hidden word, and determines win or loss conditions.
     *
     * @param input the player's input
     * @return a message indicating the result of the guess
     */
    public String processGuess(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "Please enter a letter.";
        }

        char g = Character.toLowerCase(input.trim().charAt(0));

        if (!Character.isLetter(g)) {
            return "Invalid input. Enter letters only.";
        }

        // check if already guessed
        for (int i = 0; i < guessedCount; i++) {
            if (guessed[i] == g) {
                return g + " was already guessed!";
            }
        }

        guessed[guessedCount++] = g;

        if (word.indexOf(g) >= 0) {
            updateHidden(g);

            if (hidden.equals(word)) {
                isGameOver = true;
                isWin = true;
                this.score = rewardPoints;
                return "Correct! You've won the game!";
            }
            return "Correct!";
        } else {
            wrongGuesses++;
            if (wrongGuesses >= maxIncorrect) {
                isGameOver = true;
                this.score = 0;
                return "Wrong! No tries left.";
            }
            return "Wrong! Tries remaining: " + (maxIncorrect - wrongGuesses);
        }
    }

    /**
     * Updates the hidden word by revealing correctly guessed letters.
     *
     * @param g the guessed character
     */
    private void updateHidden(char g) {
        char[] hiddenChars = hidden.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == g) {
                hiddenChars[i] = g;
            }
        }
        hidden = new String(hiddenChars);
    }

    /**
     * Returns the current game state.
     *
     * @return a string showing the hidden word and mistake count
     */
    public String getGameState() {
        return "Word: " + hidden + " | Mistakes: " + wrongGuesses + "/" + maxIncorrect;
    }

    /**
     * Checks if the game is over.
     *
     * @return true if the game has ended, otherwise false
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Checks if the player has won the game.
     *
     * @return true if the player won, otherwise false
     */
    public boolean isWin() {
        return isWin;
    }

    /**
     * Gets the score earned in the game.
     *
     * @return the score value
     */
    public int getScore() {
        return score;
    }

    /**
     * Reveals the actual word used in the game.
     *
     * @return the correct word
     */
    public String getWord() {
        return word;
    }
}