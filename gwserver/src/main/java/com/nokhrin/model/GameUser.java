package com.nokhrin.model;



public class GameUser {


    private String name;
    private int score=0;
    private boolean inGame;
    private boolean isReady=false;

    public void setName(String name) {
        this.name = name;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public GameUser(String name){
        this.name=name;
    }


    public String getName() {
        return name;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
