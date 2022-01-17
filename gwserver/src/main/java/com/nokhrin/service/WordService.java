package com.nokhrin.service;

import java.util.List;

public interface WordService {
   void getAnswer(char letter) throws InterruptedException;
   String getUnguessedWord();
   String getDesc();
   boolean isWin();
   boolean isInGame(String name);
   void createNewUser(String name);
   int getUserScore(String username);
   public String getWinner();
   public List<Character> getAvailableLetters();
   public void quit(String userName);
   public String getIngameUserName();
   boolean checkUsernameAvailability(String userName);

}
