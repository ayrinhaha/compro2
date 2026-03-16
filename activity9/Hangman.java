package com.ayrin;

import java.util.*;
import java.io.*;

public class Hangman {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        // arrays to store player names and their corresponding scores
        String[] playerNames = new String[50];
        int[] playerScores = new int[50];
        int playerCount = 0; // counter for total players

        int maxIncorrect = 10; // maximum number of incorrect guesses allowed
        int maxScore = 10; // maximum points a player can earn per game

        boolean morePlayers = true; // flag to check if there are more players

        System.out.println("===== HANGMAN GAME =====");
        System.out.println("1. Sign In");
        System.out.println("2. Sign Up");
        System.out.print("Choice: ");

        int choice = sc.nextInt();
        sc.nextLine();

        String playerName;

        if(choice == 1)
            playerName = signIn();
        else
            playerName = signUp();

        if(playerName == null){
            System.out.println("Login failed.");
            return;
        }

        // loop to allow multiple players to play
        while (morePlayers && playerCount < 50) {

            playerNames[playerCount] = playerName;

            // choose difficulty and load words
            ArrayList<String> wordBank = chooseDifficulty();

            // play a round of hangman and get the score
            int score = playGame(wordBank, maxIncorrect, maxScore);

            playerScores[playerCount] = score;

            saveScore(playerName, score);

            playerCount++;

            // ask if another player wants to play
            morePlayers = anotherPlayer();
        }

        // display the leaderboard after all players have played
        displayLeaderboard(playerNames, playerScores, playerCount);
    }

    // choose difficulty and load words from txt file
    public static ArrayList<String> chooseDifficulty() throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.println("\nSelect Difficulty");
        System.out.println("1. Easy");
        System.out.println("2. Medium");
        System.out.println("3. Hard");

        int choice = sc.nextInt();

        if(choice == 1)
            return loadWords("easy.txt");
        else if(choice == 2)
            return loadWords("medium.txt");
        else
            return loadWords("hard.txt");
    }

    // load words from txt file
    public static ArrayList<String> loadWords(String filename) throws Exception {

        ArrayList<String> wordBank = new ArrayList<>();

        File file = new File(filename);
        Scanner reader = new Scanner(file);

        while(reader.hasNextLine()){
            wordBank.add(reader.nextLine());
        }

        reader.close();

        return wordBank;
    }

    // sign in existing user
    public static String signIn() throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.print("Username: ");
        String user = sc.nextLine();

        System.out.print("Password: ");
        String pass = sc.nextLine();

        BufferedReader br = new BufferedReader(new FileReader("users.json"));

        String line;

        while((line = br.readLine()) != null){

            if(line.contains(user) && line.contains(pass)){
                br.close();
                System.out.println("Login successful!");
                return user;
            }
        }

        br.close();
        return null;
    }

    // sign up new user
    public static String signUp() throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.print("Create Username: ");
        String user = sc.nextLine();

        System.out.print("Create Password: ");
        String pass = sc.nextLine();

        FileWriter fw = new FileWriter("users.json", true);

        fw.write("{\"username\":\""+user+"\",\"password\":\""+pass+"\",\"score\":0}\n");

        fw.close();

        System.out.println("Account created!");

        return user;
    }

    // save score
    public static void saveScore(String user, int score) throws Exception {

    File file = new File("users.json");
    ArrayList<String> lines = new ArrayList<>();

    BufferedReader br = new BufferedReader(new FileReader(file));
    String line;

    while((line = br.readLine()) != null){

        if(line.contains("\"username\":\"" + user + "\"")){

            int scoreIndex = line.indexOf("\"score\":") + 8;

            int endIndex = line.indexOf("}", scoreIndex);

            int oldScore = Integer.parseInt(line.substring(scoreIndex, endIndex));

            int newScore = oldScore + score;

            line = line.substring(0, scoreIndex) + newScore + "}";
        }

        lines.add(line);
    }

    br.close();

    FileWriter fw = new FileWriter(file);

    for(String updatedLine : lines){
        fw.write(updatedLine + "\n");
    }

    fw.close();
}

    // ask if another player wants to play
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
                System.out.println("invalid input. please enter y or n only.");
        }
    }

    // play the hangman game for one player
    public static int playGame(ArrayList<String> wordBank, int maxIncorrect, int maxScore) {

        Scanner sc = new Scanner(System.in);

        // select a random word from the word bank
        String word = selectRandomWord(wordBank);

        // initialize the hidden word with asterisks
        String hiddenWord = initializeHiddenWord(word);

        char[] guessedLetters = new char[26];
        int guessedCount = 0;
        int incorrectCount = 0;
        int score = 0;

        while (incorrectCount < maxIncorrect) {

            System.out.print("Enter a letter in word " + hiddenWord +
                    " (Guesses left: " + (maxIncorrect - incorrectCount) + ") > ");

            char guess = getLetterGuess(sc);

            incorrectCount++;

            if (letterAlreadyGuessed(guess, guessedLetters, guessedCount)) {
                System.out.println(guess + " is already guessed");
                continue;
            }

            updateGuessedLetters(guess, guessedLetters, guessedCount++);

            boolean correct = isGuessCorrect(word, guess);

            if (correct) {

                hiddenWord = updateHiddenWord(word, hiddenWord, guess);

                score++;

                if(score > maxScore)
                    score = maxScore;

            } else {
                System.out.println(guess + " is not in the word");
            }

            if (isWordFullyGuessed(hiddenWord)) {

                System.out.println("\nCongratulations! You guessed the word " + word + ".");
                return maxScore;
            }
        }

        System.out.println("GAME OVER");
        System.out.println("The word is " + word + ".");

        return score;
    }

    // select a random word
    public static String selectRandomWord(ArrayList<String> wordBank) {

        int index = (int)(Math.random() * wordBank.size());
        return wordBank.get(index);
    }

    // initialize hidden word
    public static String initializeHiddenWord(String word) {

        String hidden = "";

        for(int i = 0; i < word.length(); i++)
            hidden += "*";

        return hidden;
    }

    // get a single letter guess
    public static char getLetterGuess(Scanner sc) {

        while(true){

            String input = sc.next();

            if(input.length() == 1 && Character.isLetter(input.charAt(0)))
                return Character.toLowerCase(input.charAt(0));

            System.out.print("invalid input. only letters are allowed: ");
        }
    }

    // check if letter already guessed
    public static boolean letterAlreadyGuessed(char guess, char[] guessedLetters, int guessedCount){

        for(int i = 0; i < guessedCount; i++){

            if(guessedLetters[i] == guess)
                return true;
        }

        return false;
    }

    // store guessed letter
    public static void updateGuessedLetters(char guess, char[] guessedLetters, int index){

        guessedLetters[index] = guess;
    }

    // check if guess is correct
    public static boolean isGuessCorrect(String word, char guess){

        for(int i = 0; i < word.length(); i++){

            if(word.charAt(i) == guess)
                return true;
        }

        return false;
    }

    // update hidden word
    public static String updateHiddenWord(String word, String hiddenWord, char guess){

        String result = "";

        for(int i = 0; i < word.length(); i++){

            if(word.charAt(i) == guess)
                result += guess;
            else
                result += hiddenWord.charAt(i);
        }

        return result;
    }

    // check if word fully guessed
    public static boolean isWordFullyGuessed(String hiddenWord){

        for(int i = 0; i < hiddenWord.length(); i++){

            if(hiddenWord.charAt(i) == '*')
                return false;
        }

        return true;
    }

    // display leaderboard
    public static void displayLeaderboard(String[] playerNames, int[] playerScores, int playerCount){

        System.out.println("\n===== LEADERBOARD =====");

        for(int i = 0; i < playerCount - 1; i++){

            for(int j = 0; j < playerCount - i - 1; j++){

                if(playerScores[j] < playerScores[j+1]){

                    int tempScore = playerScores[j];
                    playerScores[j] = playerScores[j+1];
                    playerScores[j+1] = tempScore;

                    String tempName = playerNames[j];
                    playerNames[j] = playerNames[j+1];
                    playerNames[j+1] = tempName;
                }
            }
        }

        for(int i = 0; i < playerCount; i++)
            System.out.println(playerNames[i] + " - " + playerScores[i] + " points");
    }
}
