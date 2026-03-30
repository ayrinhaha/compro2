package com.ayrinhaha;

import java.util.*;
import java.io.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

/**
 * Represents a user/player in the Hangman game.
 * Stores username, password, and accumulated score.
 */
class User {
    String username;
    String password;
    int score;

    /**
     * Constructs a User with the given username, password, and score.
     *
     * @param username the player's username
     * @param password the player's password
     * @param score    the player's current score
     */
    User(String username, String password, int score) {
        this.username = username;
        this.password = password;
        this.score = score;
    }
}

/**
 * Main class for the Hangman game.
 * Handles user login/signup, difficulty selection, game logic, and leaderboard.
 */
public class Hangman {

    /**
     * Main method to run the Hangman game.
     * Supports multiple players and keeps track of scores.
     *
     * @param args command-line arguments (not used)
     * @throws Exception for file handling errors
     */
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        String[] playerNames = new String[50];
        int[] playerScores = new int[50];
        int playerCount = 0;
        boolean morePlayers = true;

        System.out.println("===== HANGMAN GAME =====");
        System.out.println("1. Sign In");
        System.out.println("2. Sign Up");
        System.out.print("Choice: ");

        int choice;
        while (true) {
            try {
                choice = sc.nextInt();
                sc.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Enter 1 or 2: ");
                sc.nextLine();
            }
        }

        String playerName = (choice == 1) ? signIn() : signUp();

        if (playerName == null) {
            System.out.println("Login failed.");
            return;
        }

        while (morePlayers && playerCount < 50) {

            playerNames[playerCount] = playerName;

            Object[] data = chooseDifficulty();
            ArrayList<String> wordBank = (ArrayList<String>) data[0];
            int maxIncorrect = (int) data[1];
            int maxScore = (int) data[2];

            int score = playGame(wordBank, maxIncorrect, maxScore);
            playerScores[playerCount] = score;

            saveScore(playerName, score);

            playerCount++;

            morePlayers = anotherPlayer();
        }

        displayLeaderboard(playerNames, playerScores, playerCount);
    }

    /**
     * Allows the player to select a difficulty level.
     * Loads the corresponding word list from file.
     *
     * @return an Object array: [wordBank, maxIncorrect, maxScore]
     * @throws Exception if file reading fails
     */
    public static Object[] chooseDifficulty() throws Exception {

        Scanner sc = new Scanner(System.in);
        int choice;

        while (true) {
            try {
                System.out.println("\nSelect Difficulty");
                System.out.println("1. Easy");
                System.out.println("2. Medium");
                System.out.println("3. Hard");

                choice = sc.nextInt();
                if (choice >= 1 && choice <= 3)
                    break;
                System.out.println("Invalid choice. Try again.");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Enter numbers only.");
                sc.nextLine();
            }
        }

        if (choice == 1)
            return new Object[] { loadWords("easy.txt"), 7, 5 };
        else if (choice == 2)
            return new Object[] { loadWords("medium.txt"), 10, 7 };
        else
            return new Object[] { loadWords("hard.txt"), 13, 10 };
    }

    /**
     * Loads words from a text file into a list.
     *
     * @param filename the file containing the word list
     * @return an ArrayList of words
     * @throws Exception if file reading fails
     */
    public static ArrayList<String> loadWords(String filename) throws Exception {

        ArrayList<String> wordBank = new ArrayList<>();

        try {
            File file = new File(filename);
            Scanner reader = new Scanner(file);

            while (reader.hasNextLine()) {
                String word = reader.nextLine().trim();
                if (!word.isEmpty())
                    wordBank.add(word);
            }

            reader.close();

        } catch (FileNotFoundException e) {
            System.out.println("Error: " + filename + " not found.");
        }

        if (wordBank.isEmpty()) {
            wordBank.add("default");
        }

        return wordBank;
    }

    /**
     * Allows an existing user to sign in.
     *
     * @return the username if login is successful, otherwise null
     * @throws Exception if file reading fails
     */
    public static String signIn() throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.print("Username: ");
        String user = sc.nextLine();

        System.out.print("Password: ");
        String pass = sc.nextLine();

        Gson gson = new Gson();
        File file = new File("users.json");

        if (!file.exists()) {
            System.out.println("No users found. Please sign up first.");
            return null;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            ArrayList<User> users = gson.fromJson(reader, new TypeToken<ArrayList<User>>() {
            }.getType());
            reader.close();

            if (users != null) {
                for (User u : users) {
                    if (u.username.equals(user) && u.password.equals(pass)) {
                        System.out.println("Login successful!");
                        return user;
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error reading user data.");
        }

        return null;
    }

    /**
     * Allows a new user to sign up and stores credentials in users.json.
     *
     * @return the username if account is created, otherwise null
     * @throws Exception if file writing fails
     */
    public static String signUp() throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.print("Create Username: ");
        String user = sc.nextLine();

        System.out.print("Create Password: ");
        String pass = sc.nextLine();

        Gson gson = new Gson();
        File file = new File("users.json");

        ArrayList<User> users = new ArrayList<>();

        try {
            if (file.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                users = gson.fromJson(reader, new TypeToken<ArrayList<User>>() {
                }.getType());
                reader.close();
                if (users == null)
                    users = new ArrayList<>();
            }

            for (User u : users) {
                if (u.username.equals(user)) {
                    System.out.println("Username already exists.");
                    return null;
                }
            }

            users.add(new User(user, pass, 0));

            FileWriter writer = new FileWriter(file);
            gson.toJson(users, writer);
            writer.close();

        } catch (Exception e) {
            System.out.println("Error saving user.");
            return null;
        }

        System.out.println("Account created!");
        return user;
    }

    /**
     * Updates the user's total score in users.json.
     *
     * @param user  the username
     * @param score the score to add
     * @throws Exception if file reading or writing fails
     */
    public static void saveScore(String user, int score) throws Exception {

        Gson gson = new Gson();
        File file = new File("users.json");

        if (!file.exists())
            return;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            ArrayList<User> users = gson.fromJson(reader, new TypeToken<ArrayList<User>>() {
            }.getType());
            reader.close();

            if (users == null)
                return;

            for (User u : users) {
                if (u.username.equals(user)) {
                    u.score += score;
                    break;
                }
            }

            FileWriter writer = new FileWriter(file);
            gson.toJson(users, writer);
            writer.close();

        } catch (Exception e) {
            System.out.println("Error updating score.");
        }
    }

    /**
     * Prompts to check if another player wants to play.
     *
     * @return true if another player wants to play, otherwise false
     */
    public static boolean anotherPlayer() {

        Scanner sc = new Scanner(System.in);
        char choice;

        while (true) {

            System.out.print("Another Player? Enter y or n: ");
            choice = sc.next().toLowerCase().charAt(0);

            if (choice == 'y')
                return true;
            else if (choice == 'n')
                return false;
            else
                System.out.println("Invalid input. Please enter y or n only.");
        }
    }

    /**
     * Plays a round of Hangman for one player.
     *
     * @param wordBank     the list of possible words
     * @param maxIncorrect maximum allowed wrong guesses
     * @param maxScore     score awarded for correct guesses
     * @return the score achieved by the player
     */
    public static int playGame(ArrayList<String> wordBank, int maxIncorrect, int maxScore) {

        Scanner sc = new Scanner(System.in);

        String word = selectRandomWord(wordBank);
        String hiddenWord = initializeHiddenWord(word);

        char[] guessedLetters = new char[26];
        int guessedCount = 0;
        int incorrectCount = 0;

        while (incorrectCount < maxIncorrect) {

            System.out.print("Enter a letter in word " + hiddenWord +
                    " (Guesses left: " + (maxIncorrect - incorrectCount) + ") > ");

            char guess = getLetterGuess(sc);

            if (letterAlreadyGuessed(guess, guessedLetters, guessedCount)) {
                System.out.println(guess + " is already guessed");
                continue;
            }

            updateGuessedLetters(guess, guessedLetters, guessedCount++);

            boolean correct = isGuessCorrect(word, guess);

            if (correct) {
                hiddenWord = updateHiddenWord(word, hiddenWord, guess);
            } else {
                incorrectCount++;
                System.out.println(guess + " is not in the word");
            }

            if (isWordFullyGuessed(hiddenWord)) {
                System.out.println("\nCongratulations! You guessed the word " + word + ".");
                return maxScore;
            }
        }

        System.out.println("GAME OVER");
        System.out.println("The word is " + word + ".");

        int finalScore = maxScore - incorrectCount;
        return Math.max(finalScore, 0);
    }

    /** Selects a random word from the word bank */
    public static String selectRandomWord(ArrayList<String> wordBank) {
        int index = (int) (Math.random() * wordBank.size());
        return wordBank.get(index);
    }

    /** Initializes the hidden word with asterisks */
    public static String initializeHiddenWord(String word) {
        String hidden = "";
        for (int i = 0; i < word.length(); i++)
            hidden += "*";
        return hidden;
    }

    /** Prompts and validates a single letter guess */
    public static char getLetterGuess(Scanner sc) {
        while (true) {
            String input = sc.next();
            if (input.length() == 1 && Character.isLetter(input.charAt(0)))
                return Character.toLowerCase(input.charAt(0));
            System.out.print("Invalid input. Only letters allowed: ");
        }
    }

    /** Checks if a letter was already guessed */
    public static boolean letterAlreadyGuessed(char guess, char[] guessedLetters, int guessedCount) {
        for (int i = 0; i < guessedCount; i++) {
            if (guessedLetters[i] == guess)
                return true;
        }
        return false;
    }

    /** Stores a guessed letter */
    public static void updateGuessedLetters(char guess, char[] guessedLetters, int index) {
        guessedLetters[index] = guess;
    }

    /** Checks if the guess is correct */
    public static boolean isGuessCorrect(String word, char guess) {
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess)
                return true;
        }
        return false;
    }

    /** Updates the hidden word by revealing correct guesses */
    public static String updateHiddenWord(String word, String hiddenWord, char guess) {
        String result = "";
        for (int i = 0; i < word.length(); i++) {
            result += (word.charAt(i) == guess) ? guess : hiddenWord.charAt(i);
        }
        return result;
    }

    /** Checks if the hidden word has been fully guessed */
    public static boolean isWordFullyGuessed(String hiddenWord) {
        for (int i = 0; i < hiddenWord.length(); i++) {
            if (hiddenWord.charAt(i) == '*')
                return false;
        }
        return true;
    }

    /** Displays the leaderboard sorted by score in descending order */
    public static void displayLeaderboard(String[] playerNames, int[] playerScores, int playerCount) {
        System.out.println("\n===== LEADERBOARD =====");

        for (int i = 0; i < playerCount - 1; i++) {
            for (int j = 0; j < playerCount - i - 1; j++) {
                if (playerScores[j] < playerScores[j + 1]) {
                    int tempScore = playerScores[j];
                    playerScores[j] = playerScores[j + 1];
                    playerScores[j + 1] = tempScore;

                    String tempName = playerNames[j];
                    playerNames[j] = playerNames[j + 1];
                    playerNames[j + 1] = tempName;
                }
            }
        }

        for (int i = 0; i < playerCount; i++)
            System.out.println(playerNames[i] + " - " + playerScores[i] + " points");
    }
}