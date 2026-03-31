package com.ayrinhaha.model;

public class Match {
    
    Player p1 = new Player(null, 0, 0, 0);
    Player p2 = new Player(null, 0, 0, 0);



    public boolean isMatchComplete(int round) {
        if (round == 10) {
            return true;

        }
        return false;

    }
}
