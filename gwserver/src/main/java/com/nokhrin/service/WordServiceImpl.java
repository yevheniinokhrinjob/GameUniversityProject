package com.nokhrin.service;

import com.nokhrin.Game;
import com.nokhrin.model.GameUser;
import com.nokhrin.model.Word;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.SECONDS;

public class WordServiceImpl  implements WordService{

    Game game;
    public  WordServiceImpl (){
        game = new Game();
    }


    @Override
    public void getAnswer(char letter) throws InterruptedException {
        game.getAnswer(letter);
    }

    @Override
    public String getUnguessedWord() {
        return game.getUnguessedWord();
    }

    @Override
    public String getDesc() {
        return game.getDesc();
    }

    @Override
    public boolean isWin() {
        return game.isWin();
    }

    @Override
    public boolean isInGame(String name) {
        return game.isInGame(name);
    }

    @Override
    public void createNewUser(String name) {
        game.createNewUser(name);
    }


    @Override
    public int getUserScore(String username) {
        return game.getUserScore(username);
    }

    @Override
    public String getWinner() {
        return game.getWinner();
    }

    @Override
    public List<Character> getAvailableLetters() {
        return game.getAvailableLetters();
    }

    @Override
    public void quit(String userName) {
            game.quit(userName);
    }

    @Override
    public String getIngameUserName() {
        return game.getIngameUserName();
    }

    @Override
    public boolean checkUsernameAvailability(String userName) {
        return game.checkUsernameAvailability(userName);
    }


}
