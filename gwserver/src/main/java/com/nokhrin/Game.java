package com.nokhrin;

import com.nokhrin.model.GameUser;
import com.nokhrin.model.Word;

import java.time.LocalDateTime;
import java.util.*;

import static java.time.temporal.ChronoUnit.SECONDS;

public class Game {

    private String word;
    private String desc ="";
    private HashMap<Character,Integer> lettersWord;
    private List<Character> guessedLetters;
    private boolean isWin=true;
    private List<Character> availableLetters;
    private HashMap<String, LocalDateTime> lastRequest;
    List<GameUser> gameUsers = new ArrayList<>();

    public Game(){
        lastRequest = new HashMap<>();
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                removeNotResponseUsers();
            };
        };
        t.scheduleAtFixedRate(tt,0,5000);
    }

    private void startNewGame(){
        gameUsers.forEach(gameUser -> {gameUser.setScore(0);});
        isWin=false;
        setRandomWord();
        guessedLetters=new ArrayList<>();
        lettersWord =getLetters(word);
        availableLetters = new ArrayList<>();
        availableLetters= getAllLetters();
    }

    public List<Character>  getAllLetters(){
        List<Character> someLetters = new ArrayList<>();
        for(char i='A'; i<='Z';i++){
            someLetters.add(i);
        }
        return someLetters;
    }

    public List<Character> getAvailableLetters(){
        return availableLetters;
    }


    public String getDesc() {

        if(isWin){
            System.out.println("new game started");
            setFirstPlayer();
            startNewGame();
        }
        return desc;
    }


    public boolean isWin() {
        return isWin;
    }

    public void getAnswer(char letter) throws InterruptedException {
        availableLetters.remove(availableLetters.indexOf(letter));
        if(lettersWord.containsKey(letter)){
            int result = 100* lettersWord.get(letter);
            guessedLetters.add(letter);
            lettersWord.remove(letter);
            getIngameUser().setScore(getIngameUser().getScore()+result);
            if(lettersWord.size()==0){
                isWin=true;

            }

            //     return getIngameUser().getScore();
        }else {
            nextStep();
        }
        // return 0;
    }



    public String getUnguessedWord (){
        String resultWord="";
        for (char c: word.toUpperCase().toCharArray()) {
            if(!guessedLetters.contains(c)){
                resultWord+="_";
            }
            else{
                resultWord+=c;
            }
        }

        return  resultWord;
    }



    public int getUserScore(String username) {
        GameUser gameUser=getUserByName(username);
        lastRequest.put(username, LocalDateTime.now());
        return gameUser.getScore();
    }
    public String getWinner(){
        GameUser winner=null;
        int max=0;
        int amountOfWinners=0;
        for (GameUser user: gameUsers) {
            if(user.getScore()>max){
                max=user.getScore();
                amountOfWinners=1;
                winner=user;
            }else if(user.getScore()==max){
                amountOfWinners++;
            }
        }
        if(amountOfWinners>1){
            return "Draw";
        }else {
            if(winner!=null) {
                return "Winner is " + winner.getName();
            }else {
                return "No winner";
            }
        }
    }
    public void quit(String userName){
        if(getIngameUser().equals(getUserByName(userName))){
            nextStep();
        }
        lastRequest.remove(userName);
        gameUsers.remove(getUserByName(userName));
        if(gameUsers.size()==0){
            isWin=true;
        }
    }
    public void createNewUser(String userName){
        System.out.println(userName + " added");
        gameUsers.add(new GameUser(userName));
        System.out.println(gameUsers.size() + " size");
    }

    public boolean isInGame(String userName){
        if(getIngameUser().getName().equals(userName)){
            return true;
        }
        else {
            return false;
        }
    }

    public String getIngameUserName(){
        return getIngameUser().getName();
    }


    public boolean checkUsernameAvailability(String username){
        boolean isAvailable=true;
        for (GameUser gameUser: gameUsers) {
            if(gameUser.getName().equals(username)){
                isAvailable=false;
            }
        }
        return isAvailable;
    }
    private HashMap<Character,Integer> getLetters(String someWord){
        HashMap<Character,Integer> arr = new HashMap<Character, Integer>();
        for (char c: someWord.toUpperCase().toCharArray()) {
            if(arr.containsKey(c)){
                arr.put(c,arr.get(c).intValue()+1);
            }else {
                arr.put(c,1);
            }
        }
        return arr;
    }

    private void nextStep(){
        int nextId = 0;

        if(gameUsers.indexOf(getIngameUser())<gameUsers.size()-1){
            nextId=gameUsers.indexOf(getIngameUser())+1;
        }
        setIngameUser(nextId);

    }
    private GameUser getIngameUser(){
        GameUser gameUser = null;

        for (GameUser user: gameUsers) {
            if(user.isInGame()){
                gameUser = user;
            }
        }
        return gameUser;
    }
    private void removeNotResponseUsers(){
        List<String> usersToDelete = new ArrayList<String>();
        lastRequest.forEach((k, v) -> {
            long secDifference = Math.abs(SECONDS.between(LocalDateTime.now(), v));
            if(secDifference>5){
                //
                usersToDelete.add(k);
            }
        });
        usersToDelete.forEach(user->quit(user));
    }
    private GameUser getUserByName(String name){
        GameUser gameUser = null;
        for (GameUser user: gameUsers) {
            if(user.getName().equals(name)){
                gameUser = user;
            }
        }
        //    System.out.println(name+"xx"+gameUser.getName());
        return gameUser;
    }


    private void setIngameUser(int id){
        for (GameUser gameUser: gameUsers) {
            if(gameUsers.indexOf(gameUser)==id){
                gameUser.setInGame(true);
            }
            else{
                gameUser.setInGame(false);
            }
        }
    }

    private void setRandomWord(){
        Random rand = new Random();
        Word someWord = initWordsList().get(rand.nextInt(initWordsList().size()));
        this.word=someWord.getWord();
        this.desc=someWord.getDescription();
    }
    private List<Word> initWordsList(){
        List<Word> words = new ArrayList<>();
        words.add(new Word("Corona","Some virus"));
        words.add(new Word("Apple","Apple"));
        words.add(new Word("Hedgehog","Hedgehog"));
        return words;
    }

    private void setFirstPlayer(){
        gameUsers.get(0).setInGame(true);
    }

}
