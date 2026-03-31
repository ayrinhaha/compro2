package com.ayrinhaha.model;

public class Player {

    private String name;
    private int score;
    private int playerID;
    private int choice;

    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public Player(String name, int score, int playerID, int choice) {
        this.name = name;
        this.score = 0;
        this.playerID = playerID;
        this.choice = choice;
    }

    // public int incrementScore(){
    //     //adds score/win
    //     return score;
    // }

    public int getWinRate(int win, int loss){
        return win / (win + loss);

    }

}


